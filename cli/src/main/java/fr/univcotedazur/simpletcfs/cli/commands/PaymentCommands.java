package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.EuroTransactionDTO;
import fr.univcotedazur.simpletcfs.cli.model.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@ShellComponent
public class PaymentCommands {

    public static final String BASE_URI = "/payment/";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Pay an amount with credit card")
    public EuroTransactionDTO payWithCreditCard(String price, String creditCard, String customerName) {
        var customerID = cliContext.getCustomers().get(customerName).getId();
        EuroTransactionDTO res = restTemplate.postForObject(BASE_URI + customerID + "/payWithCreditCard", new PaymentDTO(Double.parseDouble(price), null, creditCard), EuroTransactionDTO.class);
        System.out.println("Registered customer result " + res);
        return res;
    }

    @ShellMethod("Reload the loyalty card of the customer with a credit card")
    public EuroTransactionDTO reloadLoyaltyCard(String price, String creditCard, String customerName) {
        var customerID = cliContext.getCustomers().get(customerName).getId();
        EuroTransactionDTO res = restTemplate.postForObject(BASE_URI + customerID + "/loadCard", new PaymentDTO(Double.parseDouble(price), null, creditCard), EuroTransactionDTO.class);
        System.out.println("Registered customer result " + res);
        return res;
    }

    @ShellMethod("Pay an amount with loyalty card")
    public EuroTransactionDTO payWithLoyaltyCard(String price, String customerName) {
        var customerID = cliContext.getCustomers().get(customerName).getId();
        EuroTransactionDTO res = restTemplate.postForObject(BASE_URI + customerID + "/payWithLoyaltyCard", new PaymentDTO(Double.parseDouble(price), null, null), EuroTransactionDTO.class);
        System.out.println("Registered customer result " + res);
        return res;
    }
}
