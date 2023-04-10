package fr.univcotedazur.mfc.interfaces;

import java.util.List;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.EuroTransaction;
import fr.univcotedazur.mfc.entities.Shop;

public interface EuroTransactionFinder {
    EuroTransaction find(Long id);
    List<EuroTransaction> find(Shop shop);
    List<EuroTransaction> find(Customer customer);

    List<EuroTransaction> findAll();
}

