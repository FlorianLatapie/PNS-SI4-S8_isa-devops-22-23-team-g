package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerRegistration;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRegistryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CustomerFinder customerFinder;

    private String name = "John";
    private String creditCard = "credit card number";

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void unknownCustomer() {
        assertFalse(customerFinder.findByName(name).isPresent());
    }

    @Test
    public void registerCustomer() throws Exception {
        Customer returned = customerRegistration.register(name, creditCard);
        Optional<Customer> customer = customerFinder.findByName(name);
        assertTrue(customer.isPresent());
        Customer john = customer.get();
        assertEquals(john, returned);
        assertEquals(john, customerFinder.findById(returned.getId()).get());
        assertEquals(name, john.getName());
        assertEquals(creditCard, john.getCreditCard());
    }

    @Test
    public void cannotRegisterTwice() throws Exception {
        customerRegistration.register(name, creditCard);
        Assertions.assertThrows(AlreadyExistingCustomerException.class, () -> {
            customerRegistration.register(name, creditCard);
        });
    }

}