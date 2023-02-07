package fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto;

// External DTO (Data Transfert Object) to POST payment to the external Bank system
public class PaymentDTO {

    private String creditCard;

    private double amount;

    public PaymentDTO() {
    }

    public PaymentDTO(String creditCard, double amount) {
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
