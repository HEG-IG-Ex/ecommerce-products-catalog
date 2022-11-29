package dao;

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

    public static void loadMovies() {
        String[][] moviesFromCsv = readMovies();
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
