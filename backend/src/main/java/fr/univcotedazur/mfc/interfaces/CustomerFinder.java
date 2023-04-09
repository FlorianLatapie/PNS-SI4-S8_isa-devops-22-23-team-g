package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.exceptions.CustomerNotFoundException;

public interface CustomerFinder {
    Customer login(String username) throws CustomerNotFoundException;
    Customer findCustomer(Long id) throws CustomerNotFoundException;
}

