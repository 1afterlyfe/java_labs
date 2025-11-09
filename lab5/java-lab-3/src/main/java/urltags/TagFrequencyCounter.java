package urltags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagFrequencyCounter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть URL сторінки: ");
        String urlString = scanner.nextLine();

        try {
            String html = loadHtml(urlString);
            Map<String, Integer> freq = countTags(html);

            if (freq.isEmpty()) {
                System.out.println("Теги не знайдено або сторінка порожня.");
                return;
            }

            System.out.println("\n=== Сортування за тегом (лексикографічно) ===");
            freq.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

            System.out.println("\n=== Сортування за частотою (зростання) ===");
            freq.entrySet().stream()
                    .sorted(Comparator
                            .comparingInt(Map.Entry<String, Integer>::getValue)
                            .thenComparing(Map.Entry::getKey))
                    .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

        } catch (MalformedURLException e) {
            System.out.println("Некоректний URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Помилка завантаження сторінки: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачена помилка: " + e.getMessage());
        }
    }

    private static String loadHtml(String urlString) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(urlString);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    private static Map<String, Integer> countTags(String html) {
        Map<String, Integer> freq = new HashMap<>();

        Pattern pattern = Pattern.compile("<\\s*([a-zA-Z][a-zA-Z0-9]*)\\b[^>]*>");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String tag = matcher.group(1).toLowerCase();
            if (tag.startsWith("!")) continue; // ігноруємо <!doctype ...>

            freq.put(tag, freq.getOrDefault(tag, 0) + 1);
        }
        return freq;
    }
}
