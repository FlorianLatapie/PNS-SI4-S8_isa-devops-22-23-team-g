package fr.univcotedazur.simpletcfs.entities;

import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Account {

    private String licensePlate;
    private CustomerBalance customerBalance;
    private List<Shop> favoriteShops;
    private Date lastEuroTransactionDate = new Date(Long.MIN_VALUE);

    public Customer(String username) {
        super(username);
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
}
