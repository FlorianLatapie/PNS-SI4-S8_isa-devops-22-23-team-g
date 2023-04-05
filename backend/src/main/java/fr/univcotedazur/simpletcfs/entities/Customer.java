package fr.univcotedazur.simpletcfs.entities;

import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
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

    String licensePlate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CLASSIC;

    @Embedded
    private CustomerBalance customerBalance;

    private Date lastEuroTransactionDate = new Date(0);
    public Customer() {
    }

    public Customer(String username) {
        this.username = username;
        this.customerBalance = new CustomerBalance();
    }

    public Customer(String username, String licensePlate) {
        this.username = username;
        this.licensePlate = licensePlate;
        this.customerBalance = new CustomerBalance();
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

    public void addEuro(Euro euro) throws NegativeEuroBalanceException {
        customerBalance.addEuro(euro);
    }

    public void removePoint(Point point) {
        try {
            customerBalance.removePoint(point);
        } catch (NegativePointBalanceException e) {

        }
    }

    public CustomerBalance getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(CustomerBalance customerBalance) {
        this.customerBalance = customerBalance;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
