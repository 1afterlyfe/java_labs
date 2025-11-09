package controller;

import model.Shape;
import util.ShapeFileManager;
import view.ShapeView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ShapeController {
    private Shape[] shapes;
    private final ShapeView view;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this.shapes = shapes;
        this.view = view;
    }

    public void processShapes() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            printMenu();
            System.out.print("Ваш вибір: ");

            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                view.printMessage("Будь ласка, введіть число від 0 до 9.");
                continue;
            }

            switch (choice) {
                case 1:
                    view.printShapes("Поточний набір фігур:", shapes);
                    break;
                case 2:
                    calculateAndShowTotalArea();
                    break;
                case 3:
                    calculateAndShowTotalAreaForType("Rectangle");
                    break;
                case 4:
                    calculateAndShowTotalAreaForType("Circle");
                    break;
                case 5:
                    calculateAndShowTotalAreaForType("Triangle");
                    break;
                case 6:
                    sortAndShowByArea();
                    break;
                case 7:
                    sortAndShowByColor();
                    break;
                case 8:
                    saveShapesToFile(scanner);
                    break;
                case 9:
                    loadShapesFromFile(scanner);
                    break;
                case 0:
                    view.printMessage("Завершення роботи.");
                    break;
                default:
                    view.printMessage("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1 - Показати всі фігури");
        System.out.println("2 - Сумарна площа всіх фігур");
        System.out.println("3 - Сумарна площа прямокутників");
        System.out.println("4 - Сумарна площа кіл");
        System.out.println("5 - Сумарна площа трикутників");
        System.out.println("6 - Показати фігури, відсортовані за площею");
        System.out.println("7 - Показати фігури, відсортовані за кольором");
        System.out.println("8 - Зберегти набір фігур у файл");
        System.out.println("9 - Завантажити набір фігур з файлу");
        System.out.println("0 - Вихід");
    }

    // ================== ЛОГІКА ОБЧИСЛЕНЬ ==================

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

    // ================== ЗБЕРЕЖЕННЯ / ЗАВАНТАЖЕННЯ ==================

    private void saveShapesToFile(Scanner scanner) {
        System.out.print("Введіть шлях/ім'я файлу для збереження: ");
        String filePath = scanner.nextLine();

        try {
            ShapeFileManager.saveShapes(shapes, filePath);
            view.printMessage("[OK] Набір фігур збережено у файл: " + filePath);
        } catch (IOException e) {
            view.printMessage("[ПОМИЛКА] Не вдалося зберегти фігури: " + e.getMessage());
        }
    }

    private void loadShapesFromFile(Scanner scanner) {
        System.out.print("Введіть шлях/ім'я файлу для завантаження: ");
        String filePath = scanner.nextLine();

        try {
            Shape[] loaded = ShapeFileManager.loadShapes(filePath);
            if (loaded != null && loaded.length > 0) {
                this.shapes = loaded;
                view.printMessage("[OK] Успішно завантажено " + loaded.length + " фігур(и).");
            } else {
                view.printMessage("[ПОПЕРЕДЖЕННЯ] Завантажений масив порожній.");
            }
        } catch (IOException | ClassNotFoundException e) {
            view.printMessage("[ПОМИЛКА] Не вдалося завантажити фігури: " + e.getMessage());
        }
    }
}
