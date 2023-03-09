package fr.univcotedazur.simpletcfs.connectors.eternaldto;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.Shop;

public class PaymentDTO {

    private Euro price;
    private Customer customer;
    private Shop shop;

    public PaymentDTO(Customer customer, Euro price, Shop shop) {
        this.customer = customer;
        this.shop = shop;
        this.price = price;
    }

    public PaymentDTO(Euro price) {
        this.price = price;
    }

    public Euro getPrice() {
        return price;
    }

    public void setPrice(Euro amount) {
        this.price = amount;
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
}