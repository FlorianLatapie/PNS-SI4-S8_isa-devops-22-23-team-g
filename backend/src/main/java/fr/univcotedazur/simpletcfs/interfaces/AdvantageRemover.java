package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.AdvantageNotInBalanceException;

public interface AdvantageRemover {
    void removeAdvantage(Customer customer, AdvantageItem item)throws AdvantageNotInBalanceException;
}
