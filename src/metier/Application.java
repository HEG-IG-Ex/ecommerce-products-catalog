package metier;

import static dao.DataLoader.*;

public class Application {
    public Application() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        loadMovies();
        loadAlbums();
        loadBooks();
        loadVideoGames();
    }
}
