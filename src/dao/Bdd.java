package dao;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import domaine.Product;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.beans.BeanProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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

    @BeanProperty
    private static void setClient(String URI){
        ConnectionString connectionString = new ConnectionString(URI);

        ConnectionPoolSettings connectionPoolSettings = ConnectionPoolSettings.builder()
                                                                              .minSize(2)
                                                                              .maxSize(20)
                                                                              .maxConnectionIdleTime(60, TimeUnit.SECONDS)
                                                                              .maxConnectionLifeTime(300, TimeUnit.SECONDS)
                                                                              .build();

        SocketSettings socketSettings = SocketSettings.builder()
                                                      .connectTimeout(5, TimeUnit.SECONDS)
                                                      .readTimeout(5, TimeUnit.SECONDS)
                                                      .build();

        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry fromProvider = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProvider);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                .applyConnectionString(connectionString)
                                                                .applyToConnectionPoolSettings(builder -> builder.applySettings(connectionPoolSettings))
                                                                .applyToSocketSettings(builder -> builder.applySettings(socketSettings))
                                                                .codecRegistry(pojoCodecRegistry)
                                                                .build();



        client = MongoClients.create(clientSettings);
    }

    @BeanProperty
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
            return database.getCollection(collectionName);
        }
        return null;
    }

    public static MongoDatabase getDatabase(){
        return database;
    }

    public static void closeClient(){client.close();}

    private static boolean clientIsReady(){
        return client != null;
    }

    private static boolean databaseIsReady(){
        return database != null;
    }


}
