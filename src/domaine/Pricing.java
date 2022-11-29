package domaine;

public class Pricing {
    private int sellingPrice;
    private int buyingPrice;
    private double discount;

    public Pricing(int selling_price, int buying_price, double discount) {
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
