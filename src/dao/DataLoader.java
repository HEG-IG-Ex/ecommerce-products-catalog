package dao;

import com.mongodb.client.MongoCollection;
import domaine.Pricing;
import domaine.Shipping;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

    public static Pricing generatePricingBook(){
        return new Pricing(
                generateRandomIntInRange(10, 60),
                generateRandomIntInRange(1, 10),
                Math.random()
        );
    }

    public static Shipping generateShippingBook(){
        return new Shipping(
                Math.random() + 0.3,
                191,
                136,
                15
        );
    }

    public static Document buildBookDocument(Pricing p, Shipping s, String[] book) {
        /*
        bookID
        title
        authors
        average_rating
        isbn
        isbn13
        language_code
        num_pages
        ratings_count
        text_reviews_count
        publication_date
        publisher
        */
        Document d = new Document()
                .append("type", "book")
                .append("title", book[1])
                .append("description", "")
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
                .append("authors", book[2])
                .append("average_rating", Double.parseDouble(book[3]))
                .append("isbn", Integer.parseInt(book[4]))
                .append("isbn13", Long.parseLong(book[5]))
                .append("language_code", book[6])
                .append("num_pages", Integer.parseInt(book[7]))
                .append("ratings_count", Integer.parseInt(book[8]))
                .append("text_reviews_count", Integer.parseInt(book[9]))
                .append("publication_date", parseStringForDate(book[10]))
                .append("publisher", book[11]);
        return d;
    }

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

    public static Date parseStringForDate(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date d = null;
        try{
            d = formatter.parse(value);
        }catch(ParseException pe){
            pe.printStackTrace();
        }finally {
            return d;
        }
    }

    public static double parseDoubleStringWithComma(String value){
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        double d = 0.0;
        try{
            Number number = format.parse(value);
            d = number.doubleValue();
        }catch(ParseException pe){
            pe.printStackTrace();
            d = -1.0;
        }finally {
            return d;
        }
    }

    public static Document buildMovieDocument(Pricing p, Shipping s, String[] movie) {
        Document d = new Document()
                .append("type", "movie")
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

        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");

        String[][] booksFromCsv = readBooks();
        for (int i = 1; i < booksFromCsv.length; i++) {
            Document d = buildBookDocument(generatePricingBook(), generateShippingBook(), booksFromCsv[i]);
            products.insertOne(d);
        }
    }

    public static void loadAlbums() {
        String[][] albumsFromCsv = readAlbums();
    }

    public static void loadVideoGames() {
        String[][] videoGamesFromCsv = readVideoGames();
    }
}
