package model;

import java.text.DecimalFormat;

public abstract class Shape implements Drawable {
    protected String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "Фігура: " + getClass().getSimpleName() +
                ", Колір: " + shapeColor +
                ", Площа: " + df.format(calcArea());
    }
}