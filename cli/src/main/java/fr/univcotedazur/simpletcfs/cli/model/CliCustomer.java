package fr.univcotedazur.simpletcfs.cli.model;

import java.util.UUID;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer {

    private UUID id;
    private String name;

    private int points;

    private double euros;

    public CliCustomer() {
    }

    public CliCustomer(String name) {
        this.name = name;
    }

    public UUID getId() {
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
    public String toString() {
        return "CliCustomer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", euros=" + euros +
                '}';
    }
}
