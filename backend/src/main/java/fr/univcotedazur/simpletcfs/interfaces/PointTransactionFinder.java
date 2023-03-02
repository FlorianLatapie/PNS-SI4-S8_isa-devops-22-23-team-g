package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.PointTransaction;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;
import java.util.UUID;

public interface PointTransactionFinder {
    PointTransaction findPoint(UUID id);
    List<PointTransaction> findPoint(Shop shop);
    List<PointTransaction> findPoint(Customer customer);
}

