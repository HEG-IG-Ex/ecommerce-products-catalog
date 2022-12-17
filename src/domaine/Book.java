package domaine;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

@BsonDiscriminator(value="Book", key="_cls")
public class Book extends Product{
    private List<String> authors;
    private Date releaseDate;
    private String publisher;
    private String isbn;
    private String isbn13;
    private String languageCode;
    private int numPages;

    public Book(ObjectId id, String title, Shipping shipping, Pricing pricing, Pricing rating,
                List<String> authors, Date releaseDate, String publisher, String isbn,
                String isbn13, String languageCode, int numPages) {
        super(id, title, shipping, pricing, rating);
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
