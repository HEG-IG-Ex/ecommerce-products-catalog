package metier;

import aggregation.MarginPerPeriod;
import aggregation.MostPopularGenre;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import dao.Bdd;
import domaine.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import static com.mongodb.client.model.Sorts.descending;
import static dao.DataLoader.*;
import static dao.ProductDAO.*;

public class Application {

    private Bdd bdd;

    public Application() {

        // Manage Log
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        // Clear Products Collection
        bdd = Bdd.getInstance();
        bdd.getCollection("products").deleteMany(new Document());

        // Load Data
        loadMovies();
        loadAlbums();
        loadBooks();
        loadVideoGames();

        // Execute Queries

        // Search Results
        String theStringDoISearch = "Lord of";
        ArrayList<Product> searchResult = searchStringInProducts(SearchType.Persons, theStringDoISearch);
        System.out.println("=====================================================");
        System.out.println("    SEARCH RESULT");
        System.out.println("=====================================================");
        System.out.println("You have search the string : '" + theStringDoISearch + "'\n");
        for (Product p : searchResult){
            System.out.println(p.toString());
        }


        // Most Popular Genre
        ArrayList<MostPopularGenre> mostPopularGenre = getMostPopularGenreByProductType(ProductType.Movie);
        System.out.println("=====================================================");
        System.out.println("    MOST POPULAR GENRES");
        System.out.println("=====================================================");
        for (int i = 0; i < mostPopularGenre.size(); i++) {
            System.out.println(i + ". " + mostPopularGenre.get(i).toString());
        }


        // Margin By Decades
        getMarginByReleaseDateBucket();
        ArrayList<MarginPerPeriod> marginPerPeriods = getMarginByReleaseDateBucket();
        System.out.println("=====================================================");
        System.out.println("    MOST PROFITABLES PRODUCT BY DECADES OF RELEASES");
        System.out.println("=====================================================");
        for (int i = 0; i < marginPerPeriods.size(); i++) {
            System.out.println(i + ". " + marginPerPeriods.get(i).toString());
        }

        // Close DB instance
        bdd.closeClient();

        //SRC : https://www.freecodecamp.org/news/mongodb-aggregation-framework/
        //SRC : https://kinsta.com/blog/mongodb-operators/
        //SRC : https://stackoverflow.com/questions/39320825/pojo-to-org-bson-document-and-vice-versa
        //SRC : https://mongodb.github.io/mongo-java-driver/3.9/driver/getting-started/quick-start-pojo/
        //SRC : https://www.mongodb.com/developer/languages/java/java-aggregation-pipeline/
        //SRC : https://zx77.medium.com/mongodb-java-driver-for-polymorphism-8d8a9e28ec24
        //SRC : https://www.mongodb.com/docs/drivers/java/sync/v4.3/fundamentals/crud/read-operations/sort/

    }
}


