package filters;

import java.io.*;
import java.util.Scanner;

public class CipherDemo {

    public static void encryptFile(String inputPath, String outputPath, char key) throws IOException {
        try (Reader in = new FileReader(inputPath);
             Writer out = new ShiftEncryptWriter(new FileWriter(outputPath), key)) {

            char[] buf = new char[4096];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        }
    }

    public static void decryptFile(String inputPath, String outputPath, char key) throws IOException {
        try (Reader in = new ShiftDecryptReader(new FileReader(inputPath), key);
             Writer out = new FileWriter(outputPath)) {

            char[] buf = new char[4096];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("1 - шифрувати, 2 - дешифрувати: ");
            int mode = Integer.parseInt(sc.nextLine());

            System.out.print("Введіть шлях до вхідного файлу: ");
            String inPath = sc.nextLine();
            System.out.print("Введіть шлях до вихідного файлу: ");
            String outPath = sc.nextLine();
            System.out.print("Введіть ключовий символ: ");
            char key = sc.nextLine().charAt(0);

            if (mode == 1) {
                encryptFile(inPath, outPath, key);
                System.out.println("Файл зашифровано.");
            } else if (mode == 2) {
                decryptFile(inPath, outPath, key);
                System.out.println("Файл розшифровано.");
            } else {
                System.out.println("Невірний режим.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка введення режиму.");
        } catch (IOException e) {
            System.out.println("Помилка роботи з файлами: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачена помилка: " + e.getMessage());
        }
    }
}
