package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Item;
import fr.univcotedazur.simpletcfs.entities.Order;
import fr.univcotedazur.simpletcfs.exceptions.EmptyCartException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeQuantityException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.CartModifier;
import fr.univcotedazur.simpletcfs.interfaces.CartProcessor;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CartHandler implements CartModifier, CartProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CartHandler.class);

    CustomerRepository customerRepository;

    Payment payment;

    @Autowired
    public CartHandler(CustomerRepository customerRepository, Payment payment) {
        this.customerRepository = customerRepository;
        this.payment = payment;
    }

    @Override
    public int update(Customer c, Item item) throws NegativeQuantityException {
        // some very basic logging (see the AOP way for a more powerful approach, in class ControllerLogger)
        LOG.info("TCFS:Cart-Component: Updating cart of " + c.getName() + " with " + item);

        int newQuantity = item.getQuantity();
        Set<Item> items = contents(c);
        Optional<Item> existing = items.stream().filter(e -> e.getCookie().equals(item.getCookie())).findFirst();
        if (existing.isPresent()) {
            newQuantity += existing.get().getQuantity();
        }
        if (newQuantity < 0) {
            throw new NegativeQuantityException(c.getName(), item.getCookie(), newQuantity);
        } else if (newQuantity >= 0) {
            if (existing.isPresent()) {
                items.remove(existing.get());
            }
            if (newQuantity > 0) {
                items.add(new Item(item.getCookie(), newQuantity));
            }
        }
        c.setCart(items);
        return newQuantity;
    }

    @Override
    public Set<Item> contents(Customer c) {
        return c.getCart();
    }

    @Override
    public double price(Customer c) {
        double result = 0.0;
        for (Item item : contents(c)) {
            result += (item.getQuantity() * item.getCookie().getPrice());
        }
        return result;
    }

    @Override
    public Order validate(Customer c) throws PaymentException, EmptyCartException {
        if (contents(c).isEmpty())
            throw new EmptyCartException(c.getName());
        Order newOrder = payment.payOrder(c, contents(c));
        c.setCart(new HashSet<>());
        customerRepository.save(c, c.getId());
        return newOrder;
    }


}
