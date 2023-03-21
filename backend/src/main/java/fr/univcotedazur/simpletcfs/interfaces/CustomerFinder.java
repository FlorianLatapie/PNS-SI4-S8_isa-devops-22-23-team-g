package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;

public interface CustomerFinder {
    Customer login(String username) throws CustomerNotFoundException;
    Customer findCustomer(Long id) throws CustomerNotFoundException;
}

