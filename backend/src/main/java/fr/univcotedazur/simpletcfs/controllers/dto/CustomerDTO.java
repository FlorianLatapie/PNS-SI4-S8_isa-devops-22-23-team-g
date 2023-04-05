package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Status;

import javax.validation.constraints.NotBlank;

public class CustomerDTO {

    private final Long id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned

    @NotBlank(message = "Name should not be blank")
    private final String name;

    private final int points;
    private final double euros;
    private final String status;
    private final String licensePlate;

    public CustomerDTO(Long id, String name, int points, double euros, Status status, String licensePlate) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.euros = euros;
        this.licensePlate = licensePlate;
        if(status != null) {
            this.status = status.toString();
        } else {
            this.status = "";
        }
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getUsername();
        this.points = customer.getCustomerBalance().getPointBalance().getPointAmount();
        this.euros = customer.getCustomerBalance().getEuroBalance().euroAmount();
        this.status = customer.getStatus().toString();
        this.licensePlate = customer.getLicensePlate();
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


    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", euros=" + euros +
                ", status=" + status +
                ", licensePlate=" + licensePlate +
                '}';
    }
}
