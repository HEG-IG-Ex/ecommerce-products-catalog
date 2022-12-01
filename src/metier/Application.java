package metier;

import dao.Bdd;

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


        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        loadMovies();
        loadAlbums();
        loadBooks();
        loadVideoGames();
    }
}
