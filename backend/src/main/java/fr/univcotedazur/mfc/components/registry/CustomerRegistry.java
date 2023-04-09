package fr.univcotedazur.mfc.components.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.ContactDetails;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.mfc.exceptions.CustomerNotFoundException;
import fr.univcotedazur.mfc.interfaces.CustomerFinder;
import fr.univcotedazur.mfc.interfaces.CustomerModifier;
import fr.univcotedazur.mfc.repositories.CustomerRepository;

import java.util.Optional;

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
        Optional<Customer> customerOpt = findCustomerByUsername(username);
        if (customerOpt.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists");
        }

        Customer customer = new Customer(username);
        return customerRepository.save(customer);
    }

    @Override
    public Customer signup(String username, ContactDetails contactDetails, String licensePlate) throws CustomerAlreadyExistsException {
        Optional<Customer> customerOpt = findCustomerByUsername(username);
        if (customerOpt.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists");
        }
        Customer customer = new Customer(username, licensePlate);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) throws CustomerNotFoundException {
        return null;
    }

    private Optional<Customer> findCustomerByUsername(String username) {
        return customerRepository.findCustomerByUsername(username);
    }
}
