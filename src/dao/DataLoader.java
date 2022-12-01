package dao;

import com.mongodb.client.MongoCollection;
import domaine.Pricing;
import domaine.Shipping;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

public class DataLoader {
    private static Bdd bdd;

    private final static String DATA_FOLDER = "\\data\\";
    private final static String MOVIES_FILE = "movies.csv";
    private final static String BOOKS_FILE = "books.csv";
    private final static String ALBUMS_FILE = "albums.csv";
    private final static String VIDEO_GAMES_FILE = "video_games.csv";

    public static String[][] readMovies() { return FileToStr.lireCsv(System.getProperty("user.dir") + DATA_FOLDER + MOVIES_FILE); }
    public static String[][] readBooks() { return FileToStr.lireCsv(System.getProperty("user.dir") + DATA_FOLDER + BOOKS_FILE); }
    public static String[][] readAlbums() { return FileToStr.lireCsv(System.getProperty("user.dir") +DATA_FOLDER + ALBUMS_FILE); }
    public static String[][] readVideoGames() { return FileToStr.lireCsv(System.getProperty("user.dir") +DATA_FOLDER + VIDEO_GAMES_FILE); }

    public static int generateRandomIntInRange(int min, int max){return (int)(Math.random()*(max-min+1)+min);}

    public static Pricing generatePricingMovie(){
        return new Pricing(
                generateRandomIntInRange(10, 30),
                generateRandomIntInRange(1, 10),
                Math.random()
        );
    }

    public static Shipping generateShippingMovie(){
        return new Shipping(
                0.105,
                191,
                136,
                15
        );
    }

    public static double parseDoubleStringWithComma(String value){
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try{
            Number number = format.parse(value);
            double d = number.doubleValue();
            return d;
        }catch(ParseException pe)
        {
            pe.printStackTrace();
            return -1.0;
        }
    }

    public static Document buildMovieDocument(Pricing p, Shipping s, String[] movie) {

        if(movie[1] == "Hamilton"){
            System.out.println("error");
        }

        Document d = new Document()
                .append("title", movie[1])
                .append("description", movie[7])
                .append("shipping", new Document()
                                .append("weight",s.getWeight())
                                .append("width",s.getWidth())
                                .append("heigth",s.getHeigth())
                                .append("depth",s.getDepth()))
                .append("pricing", new Document()
                        .append("selling_price",p.getSellingPrice())
                        .append("buying_price",p.getBuyingPrice())
                        .append("discount",p.getDiscount())
                )
                .append("posterLink", movie[0])
                .append("seriesTitle", movie[1])
                .append("releasedYear", Integer.parseInt(movie[2]))
                .append("certificate", movie[3])
                .append("runtime", movie[4])
                .append("genre", movie[5])
                .append("imdbRating", parseDoubleStringWithComma(movie[6]))
                .append("overview", movie[7])
                .append("metascore", parseDoubleStringWithComma(movie[8]))
                .append("direction", movie[9])
                .append("cast", Arrays.asList(movie[10], movie[11], movie[12], movie[13]))
                .append("noOfVotes", Integer.parseInt(movie[14]))
                .append("gross", movie[15] == "" ? 0 : movie[15]);
        return d;
    }


    public static void loadMovies() {

        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");

        String[][] moviesFromCsv = readMovies();
        for (int i = 1; i < moviesFromCsv.length; i++) {
            Document d = buildMovieDocument(generatePricingMovie(), generateShippingMovie(), moviesFromCsv[i]);
            products.insertOne(d);
        }

    }

    public static void loadBooks() {
        String[][] booksFromCsv = readBooks();
    }

    public static void loadAlbums() {
        String[][] albumsFromCsv = readAlbums();
    }

    public static void loadVideoGames() {
        String[][] videoGamesFromCsv = readVideoGames();
    }
}
