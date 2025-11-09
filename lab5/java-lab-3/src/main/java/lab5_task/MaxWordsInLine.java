package lab5_task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MaxWordsInLine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        String path = scanner.nextLine();

        String maxLine = "";
        int maxCount = 0;
        int lineNumber = 0;
        int maxLineNumber = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                int count = countWords(line);
                if (count > maxCount) {
                    maxCount = count;
                    maxLine = line;
                    maxLineNumber = lineNumber;
                }
            }

            if (maxLineNumber == -1) {
                System.out.println("Файл порожній або не містить слів.");
            } else {
                System.out.println("Рядок з максимальною кількістю слів:");
                System.out.println("Номер рядка: " + maxLineNumber);
                System.out.println("Кількість слів: " + maxCount);
                System.out.println("Вміст: " + maxLine);
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static int countWords(String line) {
        if (line == null) return 0;
        String trimmed = line.trim();
        if (trimmed.isEmpty()) return 0;
        String[] parts = trimmed.split("\\s+");
        return parts.length;
    }
}
