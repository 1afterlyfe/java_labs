package view;

import model.Shape;
import java.text.DecimalFormat;

public class ShapeView {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printShapes(String message, Shape[] shapes) {
        System.out.println("\n" + message);
        System.out.println("==============================================");
        if (shapes == null || shapes.length == 0) {
            System.out.println("Набір даних порожній.");
            return;
        }
        for (Shape shape : shapes) {
            System.out.println(shape);
            shape.draw();
            System.out.println("--------------------");
        }
    }

    public void printTotalArea(double totalArea) {
        System.out.println("\n[РЕЗУЛЬТАТ] Сумарна площа всіх фігур: " + df.format(totalArea));
    }

    public void printTotalAreaByType(String type, double area) {
        System.out.println("[РЕЗУЛЬТАТ] Сумарна площа фігур типу '" + type + "': " + df.format(area));
    }
}