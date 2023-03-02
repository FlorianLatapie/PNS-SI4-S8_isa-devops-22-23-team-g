package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class CustomerCommands {

    public static final String BASE_URI = "/customers";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a customer in the CoD backend (register CUSTOMER_NAME)")
    public CliCustomer register(String name) {
        System.out.println("Registering customer " + name);
        CliCustomer res = restTemplate.postForObject(BASE_URI + "/register", new CliCustomer(name), CliCustomer.class);
        System.out.println("Registered customer result " + res);
        return res;
    }

    @ShellMethod("Login a customer in the CoD backend (login CUSTOMER_NAME)")
    public CliCustomer login(String name) {
        System.out.println("Logging in customer " + name);
        CliCustomer res = restTemplate.postForObject(BASE_URI + "/login", new CliCustomer(name), CliCustomer.class);
        System.out.println("Logged in customer result " + res);
        cliContext.getCustomers().put(res.getName(), res);
        return res;
    }

    @ShellMethod("List all customers")
    public String customers() {
        return cliContext.getCustomers().toString();
    }

}
