package model;

import java.io.Serializable;
import java.text.DecimalFormat;

public abstract class Shape implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String shapeColor;
    protected static final DecimalFormat df = new DecimalFormat("#0.00");

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();
    public abstract void draw();

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "[колір=" + shapeColor +
                ", Площа=" + df.format(calcArea()) +
                "]";
    }
}
