package domaine;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Rating {
    @BsonProperty("avg_rating")
    private double averageRating;
    @BsonProperty("nb_rating")
    private int nbRating;

    //@BsonExtraElements
    private Document additionalInfo;

    @BsonCreator
    public Rating(@BsonProperty("avg_rating") double averageRating,
                  @BsonProperty("nb_rating") int nRating) {
        this.averageRating = averageRating;
        this.nbRating = nRating;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getnRating() {
        return nbRating;
    }

    public void setnRating(int nRating) {
        this.nbRating = nRating;
    }

}
