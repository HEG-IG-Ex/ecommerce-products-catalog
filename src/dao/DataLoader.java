package dao;

import domaine.Pricing;
import domaine.Shipping;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class DataLoader {
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
    public static double generateRandomDblInRange(int min, int max){return (Math.random()*(max-min+1)+min);}

    public static Pricing generatePricingMovie(){
        return new Pricing(
                generateRandomIntInRange(10, 30),
                generateRandomIntInRange(1, 10),
                generateRandomDblInRange(0, 1)
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

    public static Document buildMovieDocument(Pricing p, Shipping s, String[] movie){
        Document d = new Document()
                .append("sku", new ObjectId())
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
                .append("releasedYear", movie[2])
                .append("certificate", movie[3])
                .append("runtime", movie[4])
                .append("genre", movie[5])
                .append("imdbRating", movie[6])
                .append("overview", movie[7])
                .append("metascore", movie[8])
                .append("direction", movie[9])
                .append("cast", Arrays.asList(movie[10], movie[11], movie[12], movie[13]))
                .append("noOfVotes", movie[14])
                .append("gross", movie[15]);
        return d;
    }


    public static void loadMovies() {
        String[][] moviesFromCsv = readMovies();
        for (int i = 0; i < moviesFromCsv.length; i++) {
            buildMovieDocument(generatePricingMovie(), generateShippingMovie(), moviesFromCsv[i]);
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
