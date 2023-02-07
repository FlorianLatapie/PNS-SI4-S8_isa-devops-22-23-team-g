package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Item;
import fr.univcotedazur.simpletcfs.entities.Order;
import fr.univcotedazur.simpletcfs.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.EmptyCartException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeQuantityException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.CartModifier;
import fr.univcotedazur.simpletcfs.interfaces.CartProcessor;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerCareController.BASE_URI, produces = APPLICATION_JSON_VALUE)
// referencing the same BASE_URI as Customer care to extend it hierarchically
public class CartController {

    public static final String CART_URI = "/{customerId}/cart";

    @Autowired
    private CartModifier cart;

    @Autowired
    private CartProcessor processor;

    @Autowired
    private CustomerFinder finder;

    @PostMapping(path = CART_URI, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> updateCustomerCart(@PathVariable("customerId") UUID customerId, @RequestBody Item it) throws CustomerIdNotFoundException, NegativeQuantityException {
        int newQuantity = cart.update(retrieveCustomer(customerId), it);
        return ResponseEntity.ok(new Item(it.getCookie(), newQuantity));
    }

    @GetMapping(CART_URI)
    public ResponseEntity<Set<Item>> getCustomerCartContents(@PathVariable("customerId") UUID customerId) throws CustomerIdNotFoundException {
        return ResponseEntity.ok(processor.contents(retrieveCustomer(customerId)));
    }

    @PostMapping(path = CART_URI + "/validate")
    public ResponseEntity<String> validate(@PathVariable("customerId") UUID customerId) throws CustomerIdNotFoundException, EmptyCartException, PaymentException {
        Order order = processor.validate(retrieveCustomer(customerId));
        return ResponseEntity.ok().body("Order " + order.getId() + " (amount " + order.getPrice() +
                ") is validated");
    }

    private Customer retrieveCustomer(UUID customerId) throws CustomerIdNotFoundException {
        Optional<Customer> custopt = finder.findById(customerId);
        if (custopt.isEmpty()) {
            throw new CustomerIdNotFoundException(customerId);
        }
        return custopt.get();
    }

}
