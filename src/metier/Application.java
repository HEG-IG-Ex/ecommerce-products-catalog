package metier;

import com.mongodb.MongoCommandException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import dao.Bdd;
import domaine.Product;
import domaine.ProductType;
import domaine.SearchType;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Aggregates.unwind;
import static dao.DataLoader.*;

public class Application {

    private Bdd bdd;

    public Application() {

        // Manage Log
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        bdd = Bdd.getInstance();
        bdd.getCollection("products").drop();
        bdd.createCollection("products");

        loadMovies();
        loadAlbums();
        loadBooks();
        loadVideoGames();

        searchStringInProducts(SearchType.Title, "Lord of");

        bdd.closeClient();

        //SRC : https://www.freecodecamp.org/news/mongodb-aggregation-framework/
        //SRC : https://kinsta.com/blog/mongodb-operators/
        //SRC : https://stackoverflow.com/questions/39320825/pojo-to-org-bson-document-and-vice-versa
        //SRC : https://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start-pojo/
        //SRC : https://www.mongodb.com/developer/languages/java/java-aggregation-pipeline/

    }

    private List<Document> buildAggregationPipeline(){
        /*Bson project = new Document("$project",
                new Document("Genre", "$genres")
                        .append("title", 1L)
                        .append("ratings.avg_rating", 1L)
                        .append("type", 1L)
        );
        Bson unwind = unwind("$Genre");//new Document("$unwind", new Document("path", "$Genre"));
        Document match = new Document("$match", new Document("type", "album"));
        Document group = new Document("$group", new Document("_id", "$Genre")
                .append("count", new Document("$sum", 1L))
                .append("avg_rate", new Document("$avg", "$ratings.avg_rating")));
        Document sort = new Document("$sort",
                new Document("avg_rate", -1L));
        return Arrays.asList(project, unwind, match, group, sort);*/
        return null;
    }

    private ArrayList<Product> getMostPopularGenreByProductType(ProductType type){
        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");
        List<Document> pipeline = buildAggregationPipeline();
        List<Document> results = (List<Document>) products.aggregate(pipeline).into(new ArrayList<>());
        return null;
    }


    private ArrayList<Product> searchStringInProducts(SearchType typeSearch, String whatStringDoISearch){
        ArrayList<Product> pojoProducts = new ArrayList();

        bdd = Bdd.getInstance();
        MongoCollection<Document> products = bdd.getCollection("products");

        // Lire la type search
            // - chercher dans les titres
            // - chercher dans les auteur/artist....
        Bson idx = getIndexBySearchType(typeSearch);
        Bson filter = Filters.text(whatStringDoISearch);

        // Drop l'index de texte
        try{
            products.dropIndex("textIdx");
        }catch(MongoCommandException mce){
            mce.printStackTrace();
        }

        // Je recrée le bon index en fonction du typeSearch
        products.createIndex(idx);

        // J'exécute ma requête {$text:{$search:'<whatStringDoISearch>'}}
        FindIterable<Document> fi = products.find(filter);
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        // Je désérialize les Bson doc en Product
            // For(Document d:collections){
            //      if(d.get("type") = "movie"){
            //          Movie m = new Movie(d.get("nom"), d.get("actors"))
            //      }
            //  myArrayListOfProduct.add m
            //}


        return null;
    }

    private static Bson getIndexBySearchType(SearchType searchType){
        Bson idx = null;
        if(searchType == SearchType.Title) {
            idx = Indexes.compoundIndex(
                Indexes.text("title"),
                Indexes.text("overview")
            );
        }else{
            idx = Indexes.compoundIndex(
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


