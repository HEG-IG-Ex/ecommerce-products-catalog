package domaine;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@BsonDiscriminator(value="Album", key="_cls")
public class Album extends Product{
    @BsonProperty("artist")
    private String artist;
    @BsonProperty("release_date")
    private Date releaseDate;
    @BsonProperty("genres")
    private List<String> genres;
    @BsonProperty("descriptors")
    private List<String> descriptors;

    public Album(){
    }

    public Album(ObjectId id, String title, Shipping shipping, Pricing pricing,
                 String artist, Date releaseDate,
                 List<String> genres, List<String> descriptors) {
        super(id, title);
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.descriptors = descriptors;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<String> descriptors) {
        this.descriptors = descriptors;
    }
}
