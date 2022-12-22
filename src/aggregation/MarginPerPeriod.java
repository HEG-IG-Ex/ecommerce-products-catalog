package aggregation;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;


public class MarginPerPeriod {
    @BsonProperty("_id")
    private int year;
    @BsonProperty("count")
    private int count;
    @BsonProperty("avg_margin")
    private double averageMargin;
    @BsonProperty("products")
    private List<Document> products;

    @BsonCreator
    public MarginPerPeriod(@BsonProperty("_id") int year,
                           @BsonProperty("count") int count,
                           @BsonProperty("average_margin") double averageMargin,
                           @BsonProperty("products") List<Document> products) {
        this.year = year;
        this.count = count;
        this.averageMargin = averageMargin;
        this.products = products;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAverageMargin() {
        return averageMargin;
    }

    public void setAverageMargin(double averageMargin) {
        this.averageMargin = averageMargin;
    }

    public List<Document> getProducts() {
        return products;
    }

    public void setProducts(List<Document> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        String decades;
        decades = (this.getYear() - 10) + " - " + this.getYear();

        return decades + " - Average Margin : " + this.getAverageMargin();
    }
}
