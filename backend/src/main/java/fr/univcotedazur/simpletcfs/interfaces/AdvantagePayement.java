package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;

import java.util.List;

public interface AdvantagePayement {
    void debitAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException;
    void debitAdvantage(Customer customer, List<AdvantageItem> items) throws CustomerDoesntHaveAdvantageException;

}
