package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.AdvantageItem;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.exceptions.CustomerDoesntHaveAdvantageException;

public interface AdvantageAdder {
    void addAdvantage(Customer customer, AdvantageItem item);

    void checkAdvantagePresence(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException;
}

