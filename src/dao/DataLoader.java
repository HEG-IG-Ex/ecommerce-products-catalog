package dao;

import com.mongodb.client.MongoCollection;
import domaine.Book;
import domaine.Pricing;
import domaine.ProductType;
import domaine.Shipping;
import org.bson.Document;

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

    private static Document buildBookDocument(Pricing p, Shipping s, String[] book) {
        Document d = new Document()
                .append("_cls", "domaine.Book")
                .append("title", book[1])
                .append("shipping", new Document()
                        .append("_cls", "Shipping")
                        .append("weight",s.getWeight())
                        .append("width",s.getWidth())
                        .append("heigth",s.getHeigth())
                        .append("depth",s.getDepth()))
                .append("pricing", new Document()
                        .append("_cls", "Pricing")
                        .append("selling_price",p.getSellingPrice())
                        .append("buying_price",p.getBuyingPrice())
                        .append("discount",p.getDiscount())
                )
                .append("authors", Arrays.asList(book[2].split("/")))
                .append("release_date", parseStringForDate(book[10]))
                .append("publisher", book[11])
                .append("isbn", book[4])
                .append("isbn13",book[5])
                .append("language_code", book[6])
                .append("num_pages", Integer.parseInt(book[7]))
                .append("rating", new Document()
                        .append("avg_rating", parseDoubleStringWithComma(book[3]))
                        .append("nb_rating", Integer.parseInt(book[8]))
                        .append("nb_review", Integer.parseInt(book[9]))
                );
        return d;
    }

    private static Document buildMovieDocument(Pricing p, Shipping s, String[] movie) {
        Document d = new Document()
                .append("_cls", "domaine.Movie")
                .append("title", movie[1])
                .append("shipping", new Document()
                                .append("_cls", "Shipping")
                                .append("weight",s.getWeight())
                                .append("width",s.getWidth())
                                .append("heigth",s.getHeigth())
                                .append("depth",s.getDepth()))
                .append("pricing", new Document()
                        .append("_cls", "Pricing")
                        .append("selling_price",p.getSellingPrice())
                        .append("buying_price",p.getBuyingPrice())
                        .append("discount",p.getDiscount())
                )
                .append("overview", movie[7])
                .append("poster_link", movie[0])
                .append("release_date", Integer.parseInt(movie[2]))
                .append("certificate", movie[3])
                .append("runtime", Integer.parseInt(movie[4]))
                .append("genres", Arrays.asList(movie[5].split(", ")))
                .append("direction", movie[9])
                .append("cast", Arrays.asList(movie[10], movie[11], movie[12], movie[13]));
                if(Integer.parseInt(movie[15]) > 0){
                    d.append("gross", Integer.parseInt(movie[15]));
                }

                d.append("rating", new Document()
                    .append("avg_rating", parseDoubleStringWithComma(movie[6]))
                    .append("metascore", parseDoubleStringWithComma(movie[8]))
                    .append("nb_rating", Integer.parseInt(movie[14]))
                );

        return d;
    }

    private static Document buildAlbumDocument(Pricing p, Shipping s, String[] album) {
        Document d = new Document()
                .append("_cls", "domaine.Album")
                .append("title", album[1])
                .append("shipping", new Document()
                        .append("_cls", "Shipping")
                        .append("weight",s.getWeight())
                        .append("width",s.getWidth())
                        .append("heigth",s.getHeigth())
                        .append("depth",s.getDepth()))
                .append("pricing", new Document()
                        .append("_cls", "Pricing")
                        .append("selling_price",p.getSellingPrice())
                        .append("buying_price",p.getBuyingPrice())
                        .append("discount",p.getDiscount())
                )
                .append("artist", album[2])
                .append("release_date", parseStringForDate(album[3]))
                .append("genres", Arrays.asList(album[4].split(", ")));

                if(album[5] != "none"){
                    d.append("descriptors", Arrays.asList(album[5].split(",")));
                }
                d.append("rating", new Document()
                    .append("avg_rating",parseDoubleStringWithComma(album[6]))
                    .append("nb_rating", Integer.parseInt(album[7]))
                    .append("nb_review", Integer.parseInt(album[8]))
                    .append("rolling_stone_ranking", Integer.parseInt(album[0]))
                );
        return d;
    }

    private static Document buildVideoGameDocument(Pricing p, Shipping s, String[] videoGame) {
        Document d = new Document()
                .append("_cls", "domaine.VideoGame")
                .append("title", videoGame[1])
                .append("shipping", new Document()
                        .append("_cls", "Shipping")
                        .append("weight",s.getWeight())
                        .append("width",s.getWidth())
                        .append("heigth",s.getHeigth())
                        .append("depth",s.getDepth()))
                .append("pricing", new Document()
                        .append("_cls", "Pricing")
                        .append("selling_price",p.getSellingPrice())
                        .append("buying_price",p.getBuyingPrice())
                        .append("discount",p.getDiscount())
                )
                .append("platform", videoGame[2])
                .append("release_date", Integer.parseInt(videoGame[3]))
                .append("genres", Arrays.asList(videoGame[4]))
                .append("publisher", videoGame[5])
                .append("sales", new Document()
                        .append("noam", parseDoubleStringWithComma(videoGame[6]) * Math.pow(10.0, 6.0))
                        .append("eu", parseDoubleStringWithComma(videoGame[7]) * Math.pow(10.0, 6.0))
                        .append("jpn", parseDoubleStringWithComma(videoGame[8]) * Math.pow(10.0, 6.0))
                        .append("oth", parseDoubleStringWithComma(videoGame[9]) * Math.pow(10.0, 6.0))
                        .append("total", parseDoubleStringWithComma(videoGame[10])* Math.pow(10.0, 6.0))
                );
        return d;
    }

    public static void loadMovies() {
        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");

        String[][] moviesFromCsv = readMovies();
        for (int i = 1; i < moviesFromCsv.length; i++) {
            Document d = buildMovieDocument(
                    generatePricing(ProductType.Movie),
                    generateShipping(ProductType.Movie),
                    moviesFromCsv[i]
            );
            products.insertOne(d);
        }
    }

    public static void loadBooks() {

        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");

        String[][] booksFromCsv = readBooks();
        for (int i = 1; i < booksFromCsv.length; i++) {
            Document d = buildBookDocument(
                    generatePricing(ProductType.Book),
                    generateShipping(ProductType.Book),
                    booksFromCsv[i]
            );
            products.insertOne(d);
        }
    }

    public static void loadAlbums() {
        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");

        String[][] albumsFromCsv = readAlbums();
        for (int i = 1; i < albumsFromCsv.length; i++) {
            Document d = buildAlbumDocument(
                    generatePricing(ProductType.Album),
                    generateShipping(ProductType.Album),
                    albumsFromCsv[i]
            );
            products.insertOne(d);
        }
    }

    public static void loadVideoGames() {
        bdd = Bdd.getInstance();
        MongoCollection products = bdd.getCollection("products");

        String[][] videoGamesFromCsv = readVideoGames();
        for (int i = 1; i < videoGamesFromCsv.length; i++) {
            Document d = buildVideoGameDocument(
                    generatePricing(ProductType.VideoGame),
                    generateShipping(ProductType.VideoGame),
                    videoGamesFromCsv[i]
            );
            products.insertOne(d);
        }
    }

    public static int generateRandomIntInRange(int min, int max){return (int)(Math.random()*(max-min+1)+min);}

    public static Pricing generatePricing(ProductType type){
        Pricing p = null;
        switch(type) {
            case Movie:
                p = new Pricing(
                        generateRandomIntInRange(10, 30),
                        generateRandomIntInRange(1, 10),
                        Math.random()
                );
                break;
            case Book:
                p = new Pricing(
                        generateRandomIntInRange(10, 60),
                        generateRandomIntInRange(1, 10),
                        Math.random()
                );
                break;
            case VideoGame:
                p = new Pricing(
                        generateRandomIntInRange(30, 100),
                        generateRandomIntInRange(5, 20),
                        Math.random()
                );
                break;
            case Album:
                p = new Pricing(
                        generateRandomIntInRange(5, 15),
                        generateRandomIntInRange(1, 5),
                        Math.random()
                );
                break;
        }
        return p;
    }

    public static Shipping generateShipping(ProductType type){
        Shipping s = null;
        switch(type) {
            case Movie: case VideoGame:
                s = new Shipping(
                        0.105,
                        191,
                        136,
                        15
                );
                break;
            case Book:
                s = new Shipping(
                        Math.random() + 0.3,
                        191,
                        136,
                        15
                );
                break;
            case Album:
                s = new Shipping(
                        20,
                        120,
                        120,
                        15
                );
                break;
        }
        return s;
    }

    public static Date parseStringForDate(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE);
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

}
