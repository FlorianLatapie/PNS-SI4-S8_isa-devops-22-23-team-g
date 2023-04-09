package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;

public interface AdvantageAdder {
    void addAdvantage(Customer customer, AdvantageItem item);

    void checkAdvantagePresence(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException;
}

