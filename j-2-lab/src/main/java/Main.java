import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();

        Person p1 = new Person("Шевченко", "Тарас", 47);

        String json = gson.toJson(p1);
        System.out.println("JSON: " + json);

        Person p2 = gson.fromJson(json, Person.class);
        System.out.println("Person з JSON: " + p2);

        System.out.println("Чи рівні об'єкти? " + p1.equals(p2));
    }
}
