import exceptions.PassengerNotFoundException;
import exceptions.SeatFullException;
import people.*;
import vehicle.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTests {

    @Test
    void bus_accepts_mixed_passengers_and_counts() throws SeatFullException {
        Bus bus = new Bus(3);
        bus.board(new RegularPassenger("Alice"));
        bus.board(new Firefighter("Bob"));
        bus.board(new PoliceOfficer("Carol"));
        assertEquals(3, bus.getOccupiedSeats());
    }

    @Test
    void firetruck_accepts_only_firefighters_compile_time_guarantee() throws SeatFullException {
        FireTruck ft = new FireTruck(2);
        ft.board(new Firefighter("John"));
        ft.board(new Firefighter("Jane"));
        assertEquals(2, ft.getOccupiedSeats());
    }

    @Test
    void cannot_board_when_full() throws SeatFullException {
        Taxi taxi = new Taxi(1);
        taxi.board(new RegularPassenger("OnlyOne"));
        assertThrows(SeatFullException.class,
                () -> taxi.board(new RegularPassenger("Extra")));
    }

    @Test
    void alight_throws_if_not_present() {
        Taxi taxi = new Taxi(2);
        RegularPassenger p1 = new RegularPassenger("Ann");
        RegularPassenger p2 = new RegularPassenger("Ben");
        try {
            taxi.board(p1);
        } catch (SeatFullException ignored) {}

        assertThrows(PassengerNotFoundException.class, () -> taxi.alight(p2));
    }

    @Test
    void road_counts_people_on_mixed_vehicles() throws SeatFullException {
        Bus bus = new Bus(5);
        bus.board(new RegularPassenger("A"));
        bus.board(new Firefighter("B"));

        PoliceCar pc = new PoliceCar(2);
        pc.board(new PoliceOfficer("P1"));

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(pc);

        assertEquals(3, road.getCountOfHumans());
    }
}
