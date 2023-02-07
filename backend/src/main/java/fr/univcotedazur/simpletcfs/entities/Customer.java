package fr.univcotedazur.simpletcfs.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String creditCard;
    private Set<Item> cart = new HashSet<>();

    public Customer(String n, String c) {
        this.name = n;
        this.creditCard = c;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Set<Item> getCart() {
        return cart;
    }

    public void setCart(Set<Item> cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        if (!getId().equals(customer.getId())) return false;
        if (!getName().equals(customer.getName())) return false;
        return getCreditCard().equals(customer.getCreditCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creditCard);
    }

}
