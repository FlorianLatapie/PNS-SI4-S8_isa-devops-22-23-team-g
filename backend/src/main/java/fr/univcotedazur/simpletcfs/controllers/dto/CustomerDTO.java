package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Customer;

import javax.validation.constraints.NotBlank;

public class CustomerDTO {

    private final Long id; // expected to be empty when POSTing the creation of Customer, and containing the UUID when returned

    @NotBlank(message = "Name should not be blank")
    private final String name;

    private final int points;
    private final double euros;

    /*
    @Pattern(regexp = "\\d{10}+", message = "Credit card should be exactly 10 digits")
    private String creditCard;
     */

    public CustomerDTO(Long id, String name, int points, double euros) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.euros = euros;
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getUsername();
        this.points = customer.getCustomerBalance().getPointBalance().getPointAmount();
        this.euros = customer.getCustomerBalance().getEuroBalance().euroAmount();
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
}
