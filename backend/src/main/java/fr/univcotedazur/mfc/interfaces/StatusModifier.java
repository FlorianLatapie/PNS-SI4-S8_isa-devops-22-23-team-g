package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.EuroTransaction;

public interface StatusModifier {
    void updateStatus(Customer customer, EuroTransaction transaction);
}
