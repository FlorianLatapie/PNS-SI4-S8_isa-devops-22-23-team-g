package fr.univcotedazur.simpletcfs.entities;

import java.util.Date;
import java.util.Objects;

public class Shop {
    private String name;
    private String address;
    private Date openingTime;
    private Date closingTime;
    private IBAN iban;
    
    Long id;

    public Shop(String name, String address, Date openingTime, Date closingTime, IBAN iban) {
        this.name = name;
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.iban = iban;
    }

    public Shop() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public IBAN getIban() {
        return iban;
    }

    public void setIban(IBAN iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return name == shop.name &&
                address == shop.address &&
                openingTime == shop.openingTime &&
                closingTime == shop.closingTime &&
                iban == shop.iban;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, openingTime, closingTime, iban);
    }
}
