package domaine;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;

import java.util.List;

@BsonDiscriminator(value="Movie", key="_cls")
public class Movie extends Product{
    private String overview;
    private String posterLink;
    private int releaseDate;
    private String certificate;
    private int runtime;
    private List<String> genres;
    private String direction;
    private List<String>cast;
    private Long gross;

    public Movie(ObjectId id, String title, Shipping shipping, Pricing pricing, Pricing rating,
                 String overview, String posterLink, int releaseDate, String certificate,
                 int runtime, List<String> genres, String direction, List<String> cast, Long gross) {
        super(id, title, shipping, pricing, rating);
        this.overview = overview;
        this.posterLink = posterLink;
        this.releaseDate = releaseDate;
        this.certificate = certificate;
        this.runtime = runtime;
        this.genres = genres;
        this.direction = direction;
        this.cast = cast;
        this.gross = gross;
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
        return gross;
    }

    public void setGross(Long gross) {
        this.gross = gross;
    }
}
