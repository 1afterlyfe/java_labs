package util;

import model.Shape;

import java.io.*;

public class ShapeFileManager {

    public static void saveShapes(Shape[] shapes, String filePath) throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
        }
    }

    public static Shape[] loadShapes(String filePath)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(filePath))) {

            Object obj = ois.readObject();
            if (obj instanceof Shape[]) {
                return (Shape[]) obj;
            }
            throw new IOException("Файл не містить масив об'єктів Shape[].");
        }
    }
}

