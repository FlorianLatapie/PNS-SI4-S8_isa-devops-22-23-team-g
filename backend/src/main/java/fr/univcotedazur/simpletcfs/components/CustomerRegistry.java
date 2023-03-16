package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.ContactDetails;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class CustomerRegistry implements CustomerFinder, CustomerModifier {

    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerRegistry(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer login(String username) throws CustomerNotFoundException {
        return findCustomerByUsername(username).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    @Override
    public Customer findCustomer(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id : " + id + "not found"));
    }

    @Override
    public Customer signup(String username, ContactDetails contactDetails) throws CustomerAlreadyExistsException {
        System.out.println("Customer trying to signup: " + username);
        Optional<Customer> customerOpt = findCustomerByUsername(username);
        if (customerOpt.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists");
        }

        System.out.println("Customer not found, creating new customer: " + username);
        Customer customer = new Customer(username);
        customerRepository.save(customer);
        return customer;

    }

    // TODO: implement
    @Override
    public Customer update(Customer customer) throws CustomerNotFoundException {
        return null;
    }

    private Optional<Customer> findCustomerByUsername(String username) {
        for (Customer customer : customerRepository.findAll()) {
            if (customer.getUsername().equals(username)) {
                return Optional.of(customer);
            }
        }
        return Optional.empty();

        /* need a test to check if this works
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(customer -> customer.getUsername().equals(username))
                .findFirst().orElse(Optional.empty());
        */
    }
}
