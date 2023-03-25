package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;

public interface AdvantageAdder {
    void addAdvantage(Customer customer, AdvantageItem item);
}

