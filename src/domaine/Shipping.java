package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Shipping {
    @BsonProperty("weight")
    private double weight;
    @BsonProperty("width")
    private int width;
    @BsonProperty("heigth")
    private int heigth;
    @BsonProperty("depth")
    private int depth;

    public Shipping(){}

    @BsonCreator
    public Shipping(@BsonProperty("weight") double weight,
                    @BsonProperty("width") int width,
                    @BsonProperty("heigth") int heigth,
                    @BsonProperty("depth") int depth) {
        this.weight = weight;
        this.width = width;
        this.heigth = heigth;
        this.depth = depth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
