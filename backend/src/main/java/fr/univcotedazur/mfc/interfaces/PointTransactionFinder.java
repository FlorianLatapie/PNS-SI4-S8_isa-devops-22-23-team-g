package fr.univcotedazur.mfc.interfaces;

import java.util.List;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.PointTransaction;
import fr.univcotedazur.mfc.entities.Shop;

public interface PointTransactionFinder {
    PointTransaction findPoint(Long id);
    List<PointTransaction> findPoint(Shop shop);
    List<PointTransaction> findPoint(Customer customer);
}

