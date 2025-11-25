import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Translator {
    private Map<String, String> dictionary = new HashMap<>();

    public void addWord(String english, String ukrainian) {
        if (english == null || ukrainian == null) {
            return;
        }
        dictionary.put(english.toLowerCase(), ukrainian);
    }

    public String translateWord(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        String translated = dictionary.get(word.toLowerCase());
        return translated != null ? translated : "(немає перекладу в словнику)";
    }

    public String translatePhrase(String phrase) {
        if (phrase == null || phrase.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String[] words = phrase.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String originalWord = words[i];
            String cleanWord = originalWord.toLowerCase();

            String translated = dictionary.get(cleanWord);

            if (translated == null) {
                translated = originalWord;
            }

            result.append(translated);
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("how", "як");
        translator.addWord("are", "є");
        translator.addWord("you", "ти");
        translator.addWord("i", "я");
        translator.addWord("love", "люблю");
        translator.addWord("java", "джава");

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Оберіть операцію:");
            System.out.println("1 - Додати нове слово до словника");
            System.out.println("2 - Перекласти окреме слово");
            System.out.println("3 - Перекласти фразу");
            System.out.println("0 - Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Введіть англійське слово: ");
                    String eng = scanner.nextLine().trim();
                    if (eng.isEmpty()) {
                        System.out.println("Поле не може бути порожнім.");
                        break;
                    }

                    System.out.print("Введіть український переклад: ");
                    String ukr = scanner.nextLine().trim();
                    if (ukr.isEmpty()) {
                        System.out.println("Переклад не може бути порожнім.");
                        break;
                    }

                    translator.addWord(eng, ukr);
                    System.out.println("Додано: " + eng + " -> " + ukr);
                    break;

                case "2":
                    System.out.print("Введіть англійське слово для перекладу: ");
                    String word = scanner.nextLine().trim();
                    String translatedWord = translator.translateWord(word);
                    System.out.println("Переклад: " + translatedWord);
                    break;

                case "3":
                    System.out.print("Введіть фразу англійською: ");
                    String phrase = scanner.nextLine();
                    String translatedPhrase = translator.translatePhrase(phrase);
                    System.out.println("Переклад фрази:");
                    System.out.println(translatedPhrase);
                    break;

                case "0":
                    running = false;
                    System.out.println("Вихід із програми. До побачення!");
                    break;

                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }
}
