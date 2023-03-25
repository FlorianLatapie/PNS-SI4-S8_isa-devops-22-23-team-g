package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;

public interface EuroTransactionFinder {
    EuroTransaction find(Long id);
    List<EuroTransaction> find(Shop shop);
    List<EuroTransaction> find(Customer customer);

    List<EuroTransaction> findAll();
}

