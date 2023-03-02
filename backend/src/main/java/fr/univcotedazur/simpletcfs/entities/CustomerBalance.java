package fr.univcotedazur.simpletcfs.entities;

import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;

import java.util.ArrayList;
import java.util.List;

public class CustomerBalance {
    private Point pointBalance;
    private List<AdvantageItem> advantageItem;
    private Euro euroBalance;

    public CustomerBalance() {
        this(new Point(0), new ArrayList<>(), new Euro(0));
    }

    public CustomerBalance(Point pointBalance, List<AdvantageItem> advantageItem, Euro euroBalance) {
        this.pointBalance = pointBalance;
        this.advantageItem = advantageItem;
        this.euroBalance = euroBalance;
    }

    public Point getPointBalance() {
        return pointBalance;
    }

    public void setPointBalance(Point pointBalance) {
        this.pointBalance = pointBalance;
    }

    public List<AdvantageItem> getAdvantageItem() {
        return advantageItem;
    }

    public void setAdvantageItem(List<AdvantageItem> advantageItem) {
        this.advantageItem = advantageItem;
    }

    public void addAdvantageItem(AdvantageItem advantageItem) {
        this.advantageItem.add(advantageItem);
    }

    public Euro getEuroBalance() {
        return euroBalance;
    }

    public void setEuroBalance(Euro euroBalance) {
        this.euroBalance = euroBalance;
    }

    public void addPoint(Point point) {
        pointBalance = pointBalance.add(point);
    }

    public void removePoint(Point point) throws NegativePointBalanceException {
        Point newPointBalance = pointBalance.subtract(point);
        if (newPointBalance.getPointAmount() >= 0) {
            pointBalance = newPointBalance;
        } else {
            throw new NegativePointBalanceException();
        }
    }

    public void removeEuro(Euro amount) throws NegativeEuroBalanceException {
        Euro newEuroBalance = euroBalance.subtract(amount);
        if (newEuroBalance.centsAmount() >= 0) {
            euroBalance = newEuroBalance;
        } else {
            throw new NegativeEuroBalanceException();
        }
    }

    public void addEuro(Euro amount) throws NegativeEuroBalanceException {
        Euro newEuroBalance = euroBalance.add(amount);
        if (newEuroBalance.centsAmount() >= 0) {
            euroBalance = newEuroBalance;
        } else {
            throw new NegativeEuroBalanceException();
        }
    }


    public boolean hasPoints(Point price) {
        return pointBalance.getPointAmount() >= price.getPointAmount();
    }

    @Override
    public String toString() {
        return "CustomerBalance{" +
                "pointBalance=" + pointBalance +
                ", advantageItem=" + advantageItem +
                ", euroBalance=" + euroBalance +
                '}';
    }
}
