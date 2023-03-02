package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.AdvantageNotInBalanceException;

import java.util.List;

public interface AdvantagePayement {
    void debitAdvantage(Customer customer, AdvantageItem item) throws AdvantageNotInBalanceException;

    void debitAdvantage(Customer customer, List<AdvantageItem> items) throws AdvantageNotInBalanceException;

}
