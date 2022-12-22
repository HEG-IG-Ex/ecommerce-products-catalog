package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@BsonDiscriminator(value="domaine.Movie", key="_cls")
public class Movie extends Product{
    @BsonProperty("overview")
    private String overview;
    @BsonProperty("poster_link")
    private String posterLink;
    @BsonProperty("release_date")
    private int releaseDate;
    @BsonProperty("certificate")
    private String certificate;
    @BsonProperty("runtime")
    private int runtime;
    @BsonProperty("genres")
    private List<String> genres;
    @BsonProperty("direction")
    private String direction;
    @BsonProperty("cast")
    private List<String>cast;
    @BsonProperty("worlwide_sales")
    private Long worldwideSales;

    @BsonCreator
    public Movie(@BsonProperty("_id") ObjectId id,
                 @BsonProperty("title") String title,
                 @BsonProperty("shipping") Shipping shipping,
                 @BsonProperty("pricing") Pricing pricing,
                 @BsonProperty("overview") String overview,
                 @BsonProperty("poster_link") String posterLink,
                 @BsonProperty("release_date") int releaseDate,
                 @BsonProperty("certificate") String certificate,
                 @BsonProperty("runtime") int runtime,
                 @BsonProperty("genres") List<String> genres,
                 @BsonProperty("direction") String direction,
                 @BsonProperty("cast") List<String> cast,
                 @BsonProperty("gross") Long gross) {
        super(id, title, shipping, pricing);
        this.overview = overview;
        this.posterLink = posterLink;
        this.releaseDate = releaseDate;
        this.certificate = certificate;
        this.runtime = runtime;
        this.genres = genres;
        this.direction = direction;
        this.cast = cast;
        this.worldwideSales = gross;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public Long getGross() {
        return worldwideSales;
    }

    public void setGross(Long gross) {
        this.worldwideSales = gross;
    }

    @Override
    public String toString() {
        return super.toString() +
                "type: Album\n" +
                "direction: " + this.getDirection() + "\n" +
                "releaseDate: " + this.getReleaseDate() + "\n" +
                "cast: " + this.getCast();
    }
}
