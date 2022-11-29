package domaine;

public class Shipping {
    private double weight;
    private int width;
    private int heigth;
    private int depth;

    public Shipping(double weight, int width, int heigth, int depth) {
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
