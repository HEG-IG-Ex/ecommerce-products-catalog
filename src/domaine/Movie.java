package domaine;

public class Movie extends Product{
    private String nom;
    public Movie(String nom, Shipping shipping, Pricing pricing) {
        super(shipping, pricing);
        this.nom = nom;
    }
}
