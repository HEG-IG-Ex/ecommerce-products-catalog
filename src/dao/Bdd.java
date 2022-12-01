package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Bdd {

    private String URI_CLIENT = "mongodb://localhost:27017";
    private String DB_NAME = "ecommerce-products-catalog";

    // Static variable reference of single_instance
    // of type Singleton
    private static Bdd single_instance = null;

    private static String uriClient;
    private static String dbName;
    private static String collectionName;

    private static MongoClient client;
    private static MongoDatabase database;

    // Static method
    // Static method to create instance of Singleton class
    public static Bdd getInstance()
    {
        if (single_instance == null)
            single_instance = new Bdd();

        return single_instance;
    }

    private Bdd() {
        this.setClient(URI_CLIENT);
        this.setDatabase(DB_NAME);
    }

    private static void setClient(String URI){
        client = MongoClients.create(URI);
    }

    private static void setDatabase(String dbName){
        if(clientIsReady()){
            database = client.getDatabase(dbName);
        }
    }

    public static void createCollection( String collectionName ) {
        if(databaseIsReady()) {
            database.createCollection(collectionName);
        }
    }

    public static MongoCollection<Document> getCollection(String collectionName){
        if(databaseIsReady()){
""            return database.getCollection(collectionName);
        }
        return null;
    }

    private static boolean clientIsReady(){
        return client != null;
    }

    private static boolean databaseIsReady(){
        return database != null;
    }

}
