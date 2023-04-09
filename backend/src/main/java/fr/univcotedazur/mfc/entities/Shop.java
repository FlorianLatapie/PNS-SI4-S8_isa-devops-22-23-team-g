package fr.univcotedazur.mfc.entities;

import javax.persistence.*;

@Entity
@Table(name = "shops")
public class Shop {
    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private IBAN iban;

    @Id
    @GeneratedValue()
    Long id;

    public Shop(String name, String address, IBAN iban) {
        this.name = name;
        this.address = address;
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
