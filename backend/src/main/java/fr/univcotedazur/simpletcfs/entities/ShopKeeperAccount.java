package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.*;

@Entity
@Table(name = "shopKeeperAccounts")
public class ShopKeeperAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public ShopKeeperAccount() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShopKeeperAccount{" +
                "id=" + id +
                '}';
    }
}
