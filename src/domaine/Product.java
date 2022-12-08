package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Product {
    private ObjectId id;
    private String title;
    private Shipping shipping;
    private Pricing pricing;

    public Product(){

    }

    public Product(Shipping shipping, Pricing pricing) {
        this.shipping = shipping;
        this.pricing = pricing;
    }

    @BsonCreator
    public Product(
            @BsonProperty("_id") ObjectId id,
            @BsonProperty("title") String title,
            @BsonProperty("shipping") Shipping shipping,
            @BsonProperty("pricing") Pricing pricing) {
        this.id = id;
        this.title = title;
        this.shipping = shipping;
        this.pricing = pricing;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }


}
