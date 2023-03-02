package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;
import java.util.UUID;

public interface EuroTransactionFinder {
    EuroTransaction find(UUID id);
    List<EuroTransaction> find(Shop shop);
    List<EuroTransaction> find(Customer customer);
}

