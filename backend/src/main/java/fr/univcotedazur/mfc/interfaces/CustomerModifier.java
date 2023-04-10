package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.ContactDetails;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.mfc.exceptions.CustomerNotFoundException;

public interface CustomerModifier {
    Customer signup(String username, ContactDetails contactDetails) throws CustomerAlreadyExistsException;
    Customer signup(String username, ContactDetails contactDetails, String licensePlate) throws CustomerAlreadyExistsException;
    Customer update(Customer customer) throws CustomerNotFoundException;
}

