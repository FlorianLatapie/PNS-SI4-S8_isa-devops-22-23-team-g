package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;

public interface PointPayement{
    AdvantageItem payPoints(Customer customer, AdvantageItem item) throws NegativePointBalanceException;
}
