package metier;

import dao.Bdd;
import domaine.Product;
import domaine.ProductType;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        //SRC : https://www.freecodecamp.org/news/mongodb-aggregation-framework/
        //SRC : https://kinsta.com/blog/mongodb-operators/
    }


    private static ArrayList<Product> getMostPopularGenreByProductType(ProductType type){
        return null;
    }


    private static ArrayList<Product> searchStringInProducts(String typeSearch, String whatStringDoISearch){
        // ArrayList<Product> myArrayListOfProduct = new ArrayList();
        // Lire la type search
            // - chercher dans les titres
            // - chercher dans les auteur/artist....

        // Drop l'index de texte

        // Je recrée le bon index en fonction du typeSearch

        // J'exécute ma requête {$text:{$search:'<whatStringDoISearch>'}}

        // Je désérialize les Bson doc en Product
            // For(Document d:collections){
            //      if(d.get("type") = "movie"){
            //          Movie m = new Movie(d.get("nom"), d.get("actors"))
            //      }
            //  myArrayListOfProduct.add m
            //}


        return null;
    }
}
