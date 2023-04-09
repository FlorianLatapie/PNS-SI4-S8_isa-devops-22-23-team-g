package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.AdvantageItem;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.PointTransaction;
import fr.univcotedazur.mfc.exceptions.NegativePointBalanceException;

public interface PointPayement{
    PointTransaction payPoints(Customer customer, AdvantageItem item) throws NegativePointBalanceException;
}
