package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.ContactDetails;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;

public interface CustomerModifier {
    Customer signup(String username, ContactDetails contactDetails) throws CustomerAlreadyExistsException;
    Customer update(Customer customer) throws CustomerNotFoundException;
}

