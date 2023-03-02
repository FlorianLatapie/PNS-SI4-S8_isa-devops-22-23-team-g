package fr.univcotedazur.simpletcfs.entities;

public record Euro(int centsAmount) {

    public Euro(double amount) {
        this((int) amount * 100);
    }


    public Euro subtract(Euro amount) {
        return new Euro(centsAmount - amount.centsAmount);
    }

    public Euro add(Euro amount) {
        return new Euro(centsAmount + amount.centsAmount);
    }

    public double getEuro() {
        return centsAmount / 100.0;
    }
}
