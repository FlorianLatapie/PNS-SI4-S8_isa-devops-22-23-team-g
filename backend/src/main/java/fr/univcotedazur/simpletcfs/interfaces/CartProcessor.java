package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Item;
import fr.univcotedazur.simpletcfs.entities.Order;
import fr.univcotedazur.simpletcfs.exceptions.EmptyCartException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

import java.util.Set;

public interface CartProcessor {

    Set<Item> contents(Customer c);

    double price(Customer c);

    Order validate(Customer c) throws PaymentException, EmptyCartException;

}
