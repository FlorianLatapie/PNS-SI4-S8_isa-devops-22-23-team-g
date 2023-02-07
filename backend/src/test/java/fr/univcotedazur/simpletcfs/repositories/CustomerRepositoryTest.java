package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.simpletcfs.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer john;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        john = new Customer("john", "1234567890");
    }

    @Test
    void testSaveAndFind() {
        UUID genId = john.getId();
        assertNotNull(genId);
        customerRepository.save(john, john.getId());
        Optional<Customer> foundJohnOpt = customerRepository.findById(genId);
        assertTrue(foundJohnOpt.isPresent());
        assertEquals(john, foundJohnOpt.get());
    }

    @Test
    void testDeleteAll() {
        assertEquals(0, customerRepository.count());
        customerRepository.save(john, john.getId());
        assertEquals(1, customerRepository.count());
        customerRepository.deleteAll();
        assertEquals(0, customerRepository.count());
    }

}