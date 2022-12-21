package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;


public class MostPopularGenre {
    @BsonId()
    @BsonProperty("_id")
    private String genre;
    @BsonProperty("avg_rating")
    private double averageRating;
    @BsonProperty("count")
    private int totalProduct;
    @BsonProperty("titles")
    private List<String> titles;

    @BsonCreator
    public MostPopularGenre(@BsonProperty("_id")String genre,
                            @BsonProperty("avg_rating") double averageRating,
                            @BsonProperty("count") int totalProduct,
                            @BsonProperty("titles") List<String> titles) {
        this.genre = genre;
        this.averageRating = averageRating;
        this.totalProduct = totalProduct;
        this.titles = titles;


    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
