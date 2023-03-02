package fr.univcotedazur.simpletcfs.entities;

import java.util.Date;
import java.util.UUID;

public class PointTransaction {
    private Point pointAmount;

    private UUID id;

    private Date date;

    private String description;

    private Customer customer;

    private AdvantageItem advantageItem;

    PointTransaction(Point pointAmount, Date date, String description, Customer customer, AdvantageItem advantageItem) {
        this.id = UUID.randomUUID();
        this.pointAmount = pointAmount;
        this.date = date;
        this.description = description;
        this.customer = customer;
        this.advantageItem = advantageItem;
    }

    public Point getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(Point pointAmount) {
        this.pointAmount = pointAmount;
    }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AdvantageItem getAdvantageItem() {
        return advantageItem;
    }

    public void setAdvantageItem(AdvantageItem advantageItem) {
        this.advantageItem = advantageItem;
    }
}
