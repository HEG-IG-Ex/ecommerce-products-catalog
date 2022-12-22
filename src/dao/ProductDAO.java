package dao;

import aggregation.MarginPerPeriod;
import aggregation.MostPopularGenre;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import domaine.Product;
import domaine.ProductType;
import domaine.SearchType;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

public class ProductDAO {
    private final static Bdd bdd = Bdd.getInstance();

    /*==============================================
        MARGIN PER PERIOD
    ================================================ */
    public static ArrayList<MarginPerPeriod> getMarginByReleaseDateBucket(){
        List<Bson> pipeline = prepareAggregationPipelineForMarginByReleaseDate();
        MongoCollection<Document> products = bdd.getCollection("products");
        ArrayList<MarginPerPeriod> marginByReleaseDate = products.aggregate(pipeline, MarginPerPeriod.class).into(new ArrayList<>());
        return marginByReleaseDate;
    }

    /*==============================================
        MOST POPULAR GENRE
    ================================================ */
    public static ArrayList<MostPopularGenre> getMostPopularGenreByProductType(ProductType type) {
        List<Bson> pipeline = prepareAggregationPipelineForMostPopularGenre(translateProductTypeIntToString(type));
        MongoCollection<Document> products = bdd.getCollection("products");
        ArrayList<MostPopularGenre> mostPopularGenreByProductType = products.aggregate(pipeline, MostPopularGenre.class).into(new ArrayList<>());
        return mostPopularGenreByProductType;
    }

    /*==============================================
        SEARCH STRING
    ================================================ */
    public static ArrayList<Product> searchStringInProducts(SearchType typeSearch, String whatStringDoISearch){
        MongoCollection<Product> products = bdd.getDatabase().getCollection("products", Product.class);
        Bson filter = Filters.text(whatStringDoISearch);

        if(manageTextIndex(products, typeSearch)){
            ArrayList<Product> foundProducts = products.find(filter).into(new ArrayList<>());
            return foundProducts;
        }
        return null;

    }

    /*==============================================
        TEXT INDEX MANAGEMENT
    ================================================ */
    private static boolean manageTextIndex(MongoCollection<Product> collection, SearchType typeSearch){
        // Lire la type search
        // - chercher dans les titres
        // - chercher dans les auteur/artist...
        Bson idx = getIndexBySearchType(typeSearch);

        // Drop l'index de texte
        String indexTextName = getTextIndexName(collection);
        if(indexTextName != null){
            collection.dropIndex(indexTextName);
        }

        // Je recrée le bon index en fonction du typeSearch
        String newTextIdxName = collection.createIndex(idx);
        if(!newTextIdxName.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private static String getTextIndexName(MongoCollection<Product> collection) {
        for (Document index : collection.listIndexes()) {
            Document d = (Document) index.get("key");
            if(d.containsKey("_fts")){
                return index.getString("name");
            }
        }
        return null;
    }

    private static Bson getIndexBySearchType(SearchType searchType){
        Bson idx;
        if(searchType == SearchType.Title) {
            idx = Indexes.compoundIndex(
                    Indexes.text("title"),
                    Indexes.text("overview")
            );
        }else if(searchType == SearchType.Persons) {
            idx = Indexes.compoundIndex(
                    Indexes.text("authors"),
                    Indexes.text("cast"),
                    Indexes.text("direction"),
                    Indexes.text("artist"),
                    Indexes.text("publisher")
            );
        }else{
            idx = Indexes.compoundIndex(
                    Indexes.text("title"),
                    Indexes.text("overview"),
                    Indexes.text("authors"),
                    Indexes.text("cast"),
                    Indexes.text("direction"),
                    Indexes.text("artist"),
                    Indexes.text("publisher")
            );
        }

        return idx;
    }

    /*==============================================
        AGGREGATION PIPELINE
    ================================================ */
    private static List<Bson> prepareAggregationPipelineForMarginByReleaseDate(){

        Bson project = new Document("$project", new Document("title", 1L)
                .append("_cls", 1L)
                .append("worldwide_sales", 1L)
                .append("release_date",
                        new Document("$cond", Arrays.asList(new Document("$eq", Arrays.asList(new Document("$type", "$release_date"), "int")), "$release_date",
                                new Document("$year", "$release_date"))))
                .append("margin",
                        new Document("$subtract", Arrays.asList("$pricing.selling_price", "$pricing.buying_price"))));
        Bson bucket = new Document("$bucket",
                new Document("groupBy", "$release_date")
                        .append("boundaries", Arrays.asList(1890L, 1900L, 1910L, 1920L, 1930L, 1940L, 1950L, 1960L, 1970L, 1980L, 1990L, 2000L, 2010L, 2020L, 2030L))
                        .append("default", "Other")
                        .append("output",
                                new Document("count",
                                        new Document("$sum", 1L))
                                        .append("average_margin",
                                                new Document("$avg", "$margin"))
                                        .append("products",
                                                new Document("$push",
                                                        new Document("_cls", "$_cls")
                                                                .append("title", "$title")
                                                                .append("sales", "$worldwide_sales")))));

        Bson sort = Aggregates.sort(descending("average_margin"));

        return Arrays.asList(project,bucket,sort);

    }
    private static List<Bson> prepareAggregationPipelineForMostPopularGenre(String productType) {
        // Build Aggregation Piepline
        Bson match = Aggregates.match(Filters.eq("_cls", productType));
        // SELECT only fieds required
        Bson project = Aggregates.project(
                Projections.fields(
                        Projections.excludeId(),
                        Projections.include("genres", "_cls", "title", "rating.avg_rating", "type")
                )
        );
        // Unwind genre arrays
        Bson unwind = Aggregates.unwind("$genres");

        // Group document by genre
        //  - Create an array of product title associated to the genre
        //  - Count the number of document
        //  - Calculate the average rating for the genre
        Bson group = Aggregates.group("$genres",
                Accumulators.sum("count", 1L),
                Accumulators.avg("avg_rating", "$rating.avg_rating"),
                Accumulators.push("titles", "$title")
        );

        // Sort the document by average rating (DESC)
        Bson sort = Aggregates.sort(descending("avg_rating"));

        return Arrays.asList(match, project, unwind, group, sort);

    }

    /*==============================================
    TOOLS
    ================================================ */
    private static String translateProductTypeIntToString(ProductType type){
        // Create a HashMap object called capitalCities
        HashMap<ProductType, String> productTypeString = new HashMap<ProductType, String>();

        // Add keys and values (Country, City)
        productTypeString.put(ProductType.Movie, "Movie");
        productTypeString.put(ProductType.Album, "Album");
        productTypeString.put(ProductType.Book, "Book");
        productTypeString.put(ProductType.VideoGame, "VideoGame");

        return "domaine." + productTypeString.get(type);
    }

}
