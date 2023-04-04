package fr.univcotedazur.simpletcfs.entities;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "advantageTransactions")
public class AdvantageTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    private String advantageName;

    public AdvantageTransaction(Date date, Customer customer, String advantageName) {
        this.date = date;
        this.customer = customer;
        this.advantageName = advantageName;
    }

    public AdvantageTransaction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAdvantageName() {
        return advantageName;
    }

    public void setAdvantageName(String advantageName) {
        this.advantageName = advantageName;
    }
}