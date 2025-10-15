import controller.ShapeController;
import model.Circle;
import model.Rectangle;
import model.Shape;
import model.Triangle;
import view.ShapeView;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[]{
                new Rectangle("Червоний", 10, 5),
                new Circle("Синій", 12),
                new Triangle("Зелений", 8, 10),
                new Rectangle("Синій", 7, 14),
                new Circle("Жовтий", 5.5),
                new Triangle("Червоний", 15, 6),
                new Rectangle("Зелений", 3, 8),
                new Circle("Фіолетовий", 2),
                new Rectangle("Жовтий", 20, 10),
                new Triangle("Синій", 4, 3)
        };

        ShapeView view = new ShapeView();

        ShapeController controller = new ShapeController(shapes, view);

        controller.processShapes();
    }
}