package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@BsonDiscriminator(value="domaine.Album", key="_cls")
public class Album extends Product{

    @BsonProperty("artist")
    private String artist;
    @BsonProperty("release_date")
    private Date releaseDate;
    @BsonProperty("genres")
    private List<String> genres;
    @BsonProperty("descriptors")
    private List<String> descriptors;

    @BsonCreator
    public Album(@BsonProperty("_id") ObjectId id,
                 @BsonProperty("title") String title,
                 @BsonProperty("shipping") Shipping shipping,
                 @BsonProperty("pricing") Pricing pricing,
                 @BsonProperty("artist") String artist,
                 @BsonProperty("release_date") Date releaseDate,
                 @BsonProperty("genres") List<String> genres,
                 @BsonProperty("descriptors") List<String> descriptors) {
        super(id, title, shipping, pricing);
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

    @Override
    public String toString() {
        return super.toString() +
                "type: Album\n" +
                "artist: " + artist + "\n" +
                "releaseDate: " + releaseDate + "\n";
    }
}
