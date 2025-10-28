package vehicle;

import exceptions.PassengerNotFoundException;
import exceptions.SeatFullException;
import people.Human;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Vehicle<T extends Human> {
    private final int capacity;
    protected final List<T> passengers = new ArrayList<>();

    protected Vehicle(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
    }

    public int getCapacity() { return capacity; }
    public int getOccupiedSeats() { return passengers.size(); }
    public List<T> getPassengers() { return Collections.unmodifiableList(passengers); }

    public void board(T passenger) throws SeatFullException {
        Objects.requireNonNull(passenger, "passenger");
        if (passengers.size() >= capacity)
            throw new SeatFullException(getClass().getSimpleName() + " is full");
        passengers.add(passenger);
    }

    public void alight(T passenger) throws PassengerNotFoundException {
        Objects.requireNonNull(passenger, "passenger");
        if (!passengers.remove(passenger))
            throw new PassengerNotFoundException(passenger + " is not in " + getClass().getSimpleName());
    }
}

