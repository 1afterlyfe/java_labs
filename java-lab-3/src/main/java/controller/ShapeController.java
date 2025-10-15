package controller;

import model.Shape;
import view.ShapeView;

import java.util.Arrays;
import java.util.Comparator;

public class ShapeController {
    private Shape[] shapes;
    private ShapeView view;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this.shapes = shapes;
        this.view = view;
    }

    public void processShapes() {
        view.printShapes("Початковий набір фігур:", shapes);

        calculateAndShowTotalArea();

        calculateAndShowTotalAreaForType("Rectangle");
        calculateAndShowTotalAreaForType("Circle");
        calculateAndShowTotalAreaForType("Triangle");

        sortAndShowByArea();

        sortAndShowByColor();
    }

    private void calculateAndShowTotalArea() {
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calcArea();
        }
        view.printTotalArea(totalArea);
    }

    private void calculateAndShowTotalAreaForType(String typeName) {
        double totalAreaByType = 0;
        try {
            Class<?> shapeType = Class.forName("model." + typeName);
            for (Shape shape : shapes) {
                if (shapeType.isInstance(shape)) {
                    totalAreaByType += shape.calcArea();
                }
            }
            view.printTotalAreaByType(typeName, totalAreaByType);
        } catch (ClassNotFoundException e) {
            view.printMessage("Помилка: клас фігури '" + typeName + "' не знайдено.");
        }
    }

    private void sortAndShowByArea() {
        Shape[] sortedShapes = Arrays.copyOf(shapes, shapes.length);
        Arrays.sort(sortedShapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
        view.printShapes("Фігури, відсортовані за площею:", sortedShapes);
    }

    private void sortAndShowByColor() {
        Shape[] sortedShapes = Arrays.copyOf(shapes, shapes.length);
        Arrays.sort(sortedShapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareTo(s2.getShapeColor());
            }
        });
        view.printShapes("Фігури, відсортовані за кольором:", sortedShapes);
    }
}