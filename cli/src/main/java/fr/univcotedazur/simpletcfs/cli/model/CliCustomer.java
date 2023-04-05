package fr.univcotedazur.simpletcfs.cli.model;

import java.util.Objects;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer {

    private Long id;
    private String name;
    private int points;
    private double euros;
    private String status;
    private String licensePlate;

    public CliCustomer() {}

    public CliCustomer(String name, long id){
        this(name);
        this.id = id;
    }
    public CliCustomer(String name) {
        this.name = name;
    }

    public CliCustomer(String name, String licensePlate){
        this(name);
        this.licensePlate = licensePlate;
    }


    public Long getId() {
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

    public String getStatus() {
        return status;
    }

    public String getLicensePlate() {
        return licensePlate;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setEuros(double euros) {
        this.euros = euros;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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
        return Objects.hash(id, name, points, euros, status);
    }

    @Override
    public String toString() {
        return "CliCustomer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", euros=" + euros +
                ", status=" + status +
                ", licensePlate=" + licensePlate +
                '}';
    }
}
