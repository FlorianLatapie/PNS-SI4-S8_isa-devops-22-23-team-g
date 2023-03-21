package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table
@Entity(name = "pointTransactions")
public class PointTransaction {
    private Point pointAmount;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String description;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private AdvantageItem advantageItem;

    public PointTransaction() {

    }

    PointTransaction(Point pointAmount, Date date, String description, Customer customer, AdvantageItem advantageItem) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointTransaction that = (PointTransaction) o;
        return Objects.equals(pointAmount, that.pointAmount) && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(customer, that.customer) && Objects.equals(advantageItem, that.advantageItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointAmount, date, description, customer, advantageItem);
    }
}
