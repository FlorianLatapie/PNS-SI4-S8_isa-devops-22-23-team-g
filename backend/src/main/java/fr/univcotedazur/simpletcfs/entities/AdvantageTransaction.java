package fr.univcotedazur.simpletcfs.entities;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class AdvantageTransaction {

    private UUID id;

    private Date date;

    private Customer customer;

    private AdvantageItem advantage;

    public AdvantageTransaction(Date date, Customer customer, AdvantageItem advantage) {
        this.date = date;
        this.customer = customer;
        this.advantage = advantage;
        this.id = UUID.randomUUID();
    }

    public AdvantageTransaction() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AdvantageItem getAdvantage() {
        return advantage;
    }

    public void setAdvantage(AdvantageItem advantage) {
        this.advantage = advantage;
    }
}