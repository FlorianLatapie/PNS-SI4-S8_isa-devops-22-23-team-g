package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Euro {
    
    int centsAmount;

    public Euro(){
        
    }

    public Euro(int centsAmount) {
        this.centsAmount = centsAmount;
    }
    
    public int getCentsAmount(){
        return centsAmount;
    }
    

    public Euro(double amount) {
        this((int) amount * 100);
    }

    public Euro subtract(Euro amount) {
        return new Euro(centsAmount - amount.getCentsAmount());
    }

    public Euro add(Euro amount) {
        return new Euro(centsAmount + amount.getCentsAmount());
    }

    public double euroAmount() {
        return centsAmount / 100.0;
    }

    public void setCentsAmount(int centsAmountData) {
        this.centsAmount = centsAmountData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Euro euro = (Euro) o;
        return centsAmount == euro.centsAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centsAmount);
    }

    @Override
    public String toString() {
        return "Euro{" +
                "centsAmount=" + centsAmount +
                '}';
    }
}

