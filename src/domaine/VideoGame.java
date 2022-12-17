package domaine;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;

import java.util.List;

@BsonDiscriminator(value="VideoGame", key="_cls")
public class VideoGame extends Product {
    private String Platform;
    private int releaseDate;
    private List<String> genres;
    private String publisher;
    private Sale sale;

    public VideoGame(ObjectId id, String title, Shipping shipping, Pricing pricing, Pricing rating, String platform) {
        super(id, title, shipping, pricing, rating);
        Platform = platform;
    }
}
