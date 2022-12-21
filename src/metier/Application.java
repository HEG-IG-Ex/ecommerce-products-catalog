package metier;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import dao.Bdd;
import domaine.MostPopularGenre;
import domaine.Product;
import domaine.ProductType;
import domaine.SearchType;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import static com.mongodb.client.model.Sorts.descending;
import static dao.DataLoader.*;

public class Application {

    private Bdd bdd;

    public Application() {

        // Manage Log
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        bdd = Bdd.getInstance();
        /*bdd.getCollection("products").deleteMany(new Document());


        loadMovies();
        loadAlbums();
        loadBooks();
        loadVideoGames();

        searchStringInProducts(SearchType.Persons, "Lord of");*/
        getMostPopularGenreByProductType(ProductType.Movie);

        bdd.closeClient();

        //SRC : https://www.freecodecamp.org/news/mongodb-aggregation-framework/
        //SRC : https://kinsta.com/blog/mongodb-operators/
        //SRC : https://stackoverflow.com/questions/39320825/pojo-to-org-bson-document-and-vice-versa
        //SRC : https://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start-pojo/
        //SRC : https://www.mongodb.com/developer/languages/java/java-aggregation-pipeline/
        //SRC : https://zx77.medium.com/mongodb-java-driver-for-polymorphism-8d8a9e28ec24


    }

    private void getMostPopularGenreByProductType(ProductType type){
        bdd = Bdd.getInstance();



        Bson match = Aggregates.match(Filters.eq("_cls", "domaine.Movie"));
        Bson project = Aggregates.project(
                Projections.fields(
                        Projections.excludeId(),
                        Projections.include("genres", "_cls","title", "rating.avg_rating", "type")
                )
        );
        Bson unwind = Aggregates.unwind("$genres");
        Bson group =  Aggregates.group("$genres",
                Accumulators.sum("count", 1L),
                Accumulators.avg("avg_rating", "$rating.avg_rating"),
                Accumulators.push("titles", "$title")
        );
        Bson sort = Aggregates.sort(descending("avg_rating"));

        MongoCollection<Document> products = bdd.getCollection("products");
        List<MostPopularGenre> mostPopularGenreByProductType = products.aggregate(Arrays.asList(match,project,unwind,group,sort), MostPopularGenre.class).into(new ArrayList<>());
    }

    private ArrayList<Product> searchStringInProducts(SearchType typeSearch, String whatStringDoISearch){

        bdd = Bdd.getInstance();
        MongoCollection<Product> mongoProducts = bdd.getDatabase().getCollection("products", Product.class);
        Bson filter = Filters.text(whatStringDoISearch);

        if(manageTextIndex(mongoProducts, typeSearch)){
            return mongoProducts.find(filter).into(new ArrayList<>());
        }
        return null;

    }

    private boolean manageTextIndex(MongoCollection<Product> collection, SearchType typeSearch){
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

    private String getTextIndexName(MongoCollection<Product> collection) {
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
}


