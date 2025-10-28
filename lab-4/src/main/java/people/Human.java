package people;

import java.util.Objects;

public abstract class Human {
    private final String name;

    protected Human(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() { return name; }

    @Override public String toString() { return getClass().getSimpleName() + "(" + name + ")"; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // важливо: тип теж
        Human human = (Human) o;
        return name.equals(human.name);
    }
    @Override public int hashCode() { return Objects.hash(getClass(), name); }
}
