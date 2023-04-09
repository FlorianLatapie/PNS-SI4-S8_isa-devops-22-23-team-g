package fr.univcotedazur.mfc.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import fr.univcotedazur.mfc.cli.CliContext;
import fr.univcotedazur.mfc.cli.model.CliCustomer;

@ShellComponent
public class CustomerCommands {
    public static final String BASE_URI = "/customers";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a customer in the backend (register CUSTOMER_NAME)")
    public CliCustomer register(String name) {
        return restTemplate.postForObject(BASE_URI + "/register", new CliCustomer(name), CliCustomer.class);

    }

    @ShellMethod("Login a customer in the backend (login CUSTOMER_NAME)")
    public CliCustomer login(String name) {
        CliCustomer res = restTemplate.postForObject(BASE_URI + "/login", new CliCustomer(name), CliCustomer.class);
        if(res == null) return null;
        cliContext.getCustomers().put(res.getName(), res);
        return res;
    }

    @ShellMethod("Set the license plate for a customer in the backend (registerWithLicensePlate CUSTOMER_NAME LICENCE_PLATE)")
    public CliCustomer registerWithLicensePlate(String name, String licencePlate) {
        return restTemplate.postForObject(BASE_URI + "/registerWithLicensePlate", new CliCustomer(name, licencePlate), CliCustomer.class);
    }

    @ShellMethod("List all customers")
    public String customers() {
        return cliContext.getCustomers().toString();
    }
}
