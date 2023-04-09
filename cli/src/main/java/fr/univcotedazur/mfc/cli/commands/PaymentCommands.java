package fr.univcotedazur.mfc.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import fr.univcotedazur.mfc.cli.CliContext;
import fr.univcotedazur.mfc.cli.model.EuroTransactionDTO;
import fr.univcotedazur.mfc.cli.model.PaymentDTO;

@ShellComponent
public class PaymentCommands {

    public static final String BASE_URI = "/payment/";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Pay an amount with credit card")
    public EuroTransactionDTO payWithCreditCard(String price, String creditCard, String customerName) {
        Long customerID = cliContext.getCustomers().get(customerName).getId();
        return restTemplate.postForObject(BASE_URI + customerID + "/payWithCreditCard", new PaymentDTO(Double.parseDouble(price), 0, null, creditCard), EuroTransactionDTO.class);
    }

    @ShellMethod("Reload the loyalty card of the customer with a credit card")
    public EuroTransactionDTO reloadLoyaltyCard(String price, String creditCard, String customerName) {
        Long customerID = cliContext.getCustomers().get(customerName).getId();
        return restTemplate.postForObject(BASE_URI + customerID + "/loadCard", new PaymentDTO(Double.parseDouble(price), 0, null, creditCard), EuroTransactionDTO.class);
    }

    @ShellMethod("Pay an amount with loyalty card")
    public EuroTransactionDTO payWithLoyaltyCard(String price, String customerName) {
        Long customerID = cliContext.getCustomers().get(customerName).getId();
        return restTemplate.postForObject(BASE_URI + customerID + "/payWithLoyaltyCard", new PaymentDTO(Double.parseDouble(price), 0, null, null), EuroTransactionDTO.class);
    }
}
