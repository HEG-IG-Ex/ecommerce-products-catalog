package domaine;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import org.bson.types.ObjectId;

@BsonDiscriminator(value="domaine.Product", key="_cls")
public abstract class Product {

    @BsonId
    @BsonProperty("_id")
    private ObjectId id;
    @BsonProperty("title")
    private String title;
    @BsonProperty("shipping")
    private Shipping shipping;
    @BsonProperty("pricing")
    private Pricing pricing;

    @BsonCreator
    public Product(@BsonProperty("_id") ObjectId id,
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
