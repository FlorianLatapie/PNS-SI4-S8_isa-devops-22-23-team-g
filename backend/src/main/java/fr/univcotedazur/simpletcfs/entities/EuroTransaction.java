package fr.univcotedazur.simpletcfs.entities;


import java.util.UUID;

public class EuroTransaction {
    private Customer customer;
    private Euro price;
    private Shop shop;
    private UUID id;
    private final Point pointEarned;

    public EuroTransaction(Customer customer, Euro price) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.price = price;
        this.pointEarned = new Point(0);
    }

    public EuroTransaction(Customer customer, Shop shop, Euro price) {
        this(customer, price);
        this.shop = shop;
    }

    public EuroTransaction(Customer customer, Shop shop, Euro price, Point pointEarned) {
        this.customer = customer;
        this.shop = shop;
        this.price = price;
        this.pointEarned = pointEarned;
        this.id = UUID.randomUUID();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Euro getPrice() {
        return price;
    }

    public void setPrice(Euro price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Point getPointEarned() {
        return pointEarned;
    }
}
