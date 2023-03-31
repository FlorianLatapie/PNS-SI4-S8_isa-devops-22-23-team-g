package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;

public interface StatusModifier {
    void updateStatus(Customer customer, EuroTransaction transaction);
}
