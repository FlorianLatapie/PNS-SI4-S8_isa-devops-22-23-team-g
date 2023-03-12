package fr.univcotedazur.simpletcfs.cli.model;

import java.util.Objects;
import java.util.UUID;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer {

    private long id;
    private String name;

    private int points;

    private double euros;

    public CliCustomer() {
    }

    public CliCustomer(String name, long id){
        this(name);
        this.id = id;
    }
    public CliCustomer(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public double getEuros() {
        return euros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliCustomer that = (CliCustomer) o;
        return points == that.points && Double.compare(that.euros, euros) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, points, euros);
    }

    @Override
    public String toString() {
        return "CliCustomer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", euros=" + euros +
                '}';
    }
}
