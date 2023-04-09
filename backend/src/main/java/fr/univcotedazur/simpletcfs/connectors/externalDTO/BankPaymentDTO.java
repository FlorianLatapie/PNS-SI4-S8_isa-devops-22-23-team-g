package fr.univcotedazur.simpletcfs.connectors.externaldto;

public class BankPaymentDTO {
    private String creditCard;
    private double amount;

    public BankPaymentDTO() {
    }

    public BankPaymentDTO(String creditCard, double amount) {
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
