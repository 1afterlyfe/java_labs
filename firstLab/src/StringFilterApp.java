import java.util.ArrayList;
import java.util.List;

public class StringFilterApp {

    public static void main(String[] args) {
        String[] input = {"Hello", "World", "Java", "Programming", "Language"};

        double averageLength = calculateAverageLength(input);
        System.out.println("Середня довжина рядків: " + averageLength);

        String[] shorter = filterByLength(input, averageLength, true);
        String[] longer = filterByLength(input, averageLength, false);

        System.out.println("\nРядки, довжина яких менша за середню:");
        printArray(shorter);

        System.out.println("\nРядки, довжина яких більша за середню:");
        printArray(longer);
    }

    private static double calculateAverageLength(String[] arr) {
        int totalLength = 0;
        for (String s : arr) {
            totalLength += s.length();
        }
        return (double) totalLength / arr.length;
    }

    private static String[] filterByLength(String[] arr, double average, boolean shorter) {
        List<String> result = new ArrayList<>();
        for (String s : arr) {
            if (shorter && s.length() < average) {
                result.add(s);
            } else if (!shorter && s.length() > average) {
                result.add(s);
            }
        }
        return result.toArray(new String[0]);
    }

    private static void printArray(String[] arr) {
        for (String s : arr) {
            System.out.println(s);
        }
    }
}
