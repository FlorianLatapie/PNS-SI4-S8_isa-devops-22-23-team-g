package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.interfaces.CustomerRegistration;
import fr.univcotedazur.simpletcfs.interfaces.OrderProcessing;
import fr.univcotedazur.simpletcfs.interfaces.Tracker;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import fr.univcotedazur.simpletcfs.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KitchenTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private CustomerRegistration registry;

    @Autowired
    private OrderProcessing processor;

    @Autowired
    private Tracker tracker;

    private Set<Item> items;
    private Customer john;

    @BeforeEach
    public void setUpContext() throws Exception {
        customerRepository.deleteAll();
        orderRepository.deleteAll();
        items = new HashSet<>();
        items.add(new Item(Cookies.CHOCOLALALA, 3));
        items.add(new Item(Cookies.DARK_TEMPTATION, 2));
        john = registry.register("john", "1234-896983");
    }

    @Test
    void processCommand() throws Exception {
        Order inProgress = new Order(john, items);
        processor.process(inProgress);
        assertEquals(OrderStatus.IN_PROGRESS, tracker.retrieveStatus(inProgress.getId()));
    }

}