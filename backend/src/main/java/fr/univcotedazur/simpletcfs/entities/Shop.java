package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "shops")
public class Shop {
    private String name;
    private String address;
    // private Date openingTime;
    // private Date closingTime;

    @Enumerated(EnumType.STRING)
    private IBAN iban;

    @Id
    @GeneratedValue()
    Long id;

    public Shop(String name, String address, IBAN iban) {
        this.name = name;
        this.address = address;
        //this.openingTime = openingTime;
        //this.closingTime = closingTime;
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

    public IBAN getIban() {
        return iban;
    }

    public void setIban(IBAN iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", iban=" + iban +
                ", id=" + id +
                '}';
    }
}
