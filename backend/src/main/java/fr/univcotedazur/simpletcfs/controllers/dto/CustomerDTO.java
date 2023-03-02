package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Customer;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class CustomerDTO {

    private final UUID id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned

    @NotBlank(message = "name should not be blank")
    private final String name;

    private final int points;
    private final double euros;

    /*
    @Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits")
    private String creditCard;
     */

    public CustomerDTO(UUID id, String name, int points, double euros) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.euros = euros;
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getUsername();
        this.points = customer.getCustomerBalance().getPointBalance().pointAmount();
        this.euros = customer.getCustomerBalance().getEuroBalance().getEuro();
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
}
