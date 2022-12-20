package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

@BsonDiscriminator(value="Book", key="_cls")
public class Book extends Product{
    @BsonProperty("authors")
    private List<String> authors;
    @BsonProperty("release_date")
    private Date releaseDate;
    @BsonProperty("publisher")
    private String publisher;
    @BsonProperty("isbn")
    private String isbn;
    @BsonProperty("isbn13")
    private String isbn13;
    @BsonProperty("language_code")
    private String languageCode;
    @BsonProperty("num_pages")
    private int numPages;


    @BsonCreator
    public Book(@BsonProperty("_id") ObjectId id,
                @BsonProperty("title")String title,
                @BsonProperty("authors") List<String> authors,
                @BsonProperty("release_date") Date releaseDate,
                @BsonProperty("publisher") String publisher,
                @BsonProperty("isbn") String isbn,
                @BsonProperty("isbn13") String isbn13,
                @BsonProperty("language_code") String languageCode,
                @BsonProperty("num_pages") int numPages) {
        super(id, title);
        this.authors = authors;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.languageCode = languageCode;
        this.numPages = numPages;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}
