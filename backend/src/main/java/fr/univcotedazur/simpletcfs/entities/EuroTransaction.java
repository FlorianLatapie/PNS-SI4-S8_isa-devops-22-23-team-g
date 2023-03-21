package fr.univcotedazur.simpletcfs.entities;


import javax.persistence.*;
import java.util.Objects;
import java.util.Date;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="euroTransactions")
public class EuroTransaction {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @Embedded
    private Euro price;
    @Transient
    private Shop shop;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Point pointEarned;

    private Date date;

    public EuroTransaction() {

    }

    public EuroTransaction(Customer customer, Euro price) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getPointEarned() {
        return pointEarned;
    }

    public void setPointEarned(Point pointEarned) {
        this.pointEarned = pointEarned;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuroTransaction that = (EuroTransaction) o;
        return Objects.equals(customer, that.customer) && Objects.equals(price, that.price) && Objects.equals(shop, that.shop) && Objects.equals(pointEarned, that.pointEarned) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, price, shop, pointEarned, date);
    }
}
