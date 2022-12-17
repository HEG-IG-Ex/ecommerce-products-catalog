package domaine;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.Date;
import java.util.List;

@BsonDiscriminator(value="Album", key="_cls")
public class Album {
    private String artist;
    private Date releaseDate;
    private List<String> genres;
    private List<String> descriptors;

    public Album(String artist, Date releaseDate, List<String> genres, List<String> descriptors) {
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
