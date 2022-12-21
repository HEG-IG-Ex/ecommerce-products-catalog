package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@BsonDiscriminator(value="domaine.VideoGame", key="_cls")
public class VideoGame extends Product {
    @BsonProperty("platform")
    private String platform;
    @BsonProperty("release_date")
    private int releaseDate;
    @BsonProperty("genres")
    private List<String> genres;
    @BsonProperty("publisher")
    private String publisher;
    @BsonProperty("sale")
    private Sale sale;

    @BsonCreator
    public VideoGame(@BsonProperty("_id") ObjectId id,
                     @BsonProperty("title") String title,
                     @BsonProperty("shipping") Shipping shipping,
                     @BsonProperty("pricing") Pricing pricing,
                     @BsonProperty("platform") String platform,
                     @BsonProperty("release_date") int releaseDate,
                     @BsonProperty("genres") List<String> genres,
                     @BsonProperty("publisher") String publisher,
                     @BsonProperty("sale") Sale sale) {
        super(id, title, shipping, pricing);
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.publisher = publisher;
        this.sale = sale;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }


}

