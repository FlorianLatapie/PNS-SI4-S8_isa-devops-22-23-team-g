package fr.univcotedazur.simpletcfs.entities;

import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;
import javax.persistence.*;

import java.util.*;

@Entity
@Table(name= "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String username;

    private String licensePlate;
    @Embedded
    private CustomerBalance customerBalance;
    @Transient
    private List<Shop> favoriteShops;
    @Transient
    private Date lastEuroTransactionDate = new Date(Long.MIN_VALUE);
    public Customer() {
    }


    public Customer(String username) {
        this.username = username;
        this.customerBalance = new CustomerBalance();
        this.favoriteShops = new ArrayList<>();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void addPoint(Point point) {
        customerBalance.addPoint(point);
    }

    public void removePoint(Point point) {
        try {
            customerBalance.removePoint(point);
        } catch (NegativePointBalanceException e) {
            e.printStackTrace();
        }
    }

    public CustomerBalance getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(CustomerBalance customerBalance) {
        this.customerBalance = customerBalance;
    }

    public List<Shop> getFavoriteShops() {
        return favoriteShops;
    }

    public void setFavoriteShops(List<Shop> favoriteShops) {
        this.favoriteShops = favoriteShops;
    }

    public Date getLastEuroTransactionDate() {
        return lastEuroTransactionDate;
    }

    public void setLastEuroTransactionDate(Date lastEuroTransactionDate) {
        this.lastEuroTransactionDate = lastEuroTransactionDate;
    }

    public List<AdvantageItem> getAdvantageItems() {
        return customerBalance.getAdvantageItem();
    }

    @Override
    public String toString() {
        return "Customer{" + "customerBalance=" + customerBalance + ", id=" + id + ", username='" + username + "'}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(username, customer.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
