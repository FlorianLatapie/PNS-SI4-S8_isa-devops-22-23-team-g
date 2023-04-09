package fr.univcotedazur.mfc.entities;

import javax.persistence.*;

import fr.univcotedazur.mfc.controllers.dto.CustomerDTO;
import fr.univcotedazur.mfc.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.mfc.exceptions.NegativePointBalanceException;

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
    
    public Customer(CustomerDTO customerDto) {
        this.username = customerDto.getName();
        this.licensePlate = customerDto.getLicensePlate();
        this.id = customerDto.getId();
        Point points = new Point(customerDto.getPoints());
        Euro euros = new Euro(customerDto.getEuros());
        this.customerBalance = new CustomerBalance(points, euros);
        this.status = Status.valueOf(customerDto.getStatus());
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

    public void removePoint(Point point) throws NegativePointBalanceException {
        customerBalance.removePoint(point);
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
