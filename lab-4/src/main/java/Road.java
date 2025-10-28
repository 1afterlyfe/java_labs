import people.Human;
import vehicle.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class Road {
    public final List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    public int getCountOfHumans() {
        int sum = 0;
        for (Vehicle<? extends Human> v : carsInRoad) {
            sum += v.getOccupiedSeats();
        }
        return sum;
    }

    public void addCarToRoad(Vehicle<? extends Human> vehicle) {
        carsInRoad.add(vehicle);
    }
}
