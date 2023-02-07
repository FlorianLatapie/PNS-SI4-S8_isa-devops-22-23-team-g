package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Item;
import fr.univcotedazur.simpletcfs.entities.Order;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

import java.util.Set;

public interface Payment {

    Order payOrder(Customer customer, Set<Item> items) throws PaymentException;

}
