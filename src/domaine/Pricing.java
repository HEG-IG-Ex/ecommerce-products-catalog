package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Pricing {
    @BsonProperty("selling_price")
    private int sellingPrice;
    @BsonProperty("buying_price")
    private int buyingPrice;
    @BsonProperty("discount")
    private double discount;

    public Pricing(){}

    @BsonCreator
    public Pricing(@BsonProperty("selling_price") int selling_price,
                   @BsonProperty("buying_price") int buying_price,
                   @BsonProperty("discount") double discount) {
        this.sellingPrice = selling_price;
        this.buyingPrice = buying_price;
        this.discount = discount;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int selling_price) {
        this.sellingPrice = selling_price;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(int buying_price) {
        this.buyingPrice = buying_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
