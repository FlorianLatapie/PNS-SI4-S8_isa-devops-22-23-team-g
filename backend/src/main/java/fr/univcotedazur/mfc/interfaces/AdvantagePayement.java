package fr.univcotedazur.mfc.interfaces;

import java.util.List;

import fr.univcotedazur.mfc.entities.AdvantageItem;
import fr.univcotedazur.mfc.entities.AdvantageTransaction;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.mfc.exceptions.ParkingException;

public interface AdvantagePayement {
    AdvantageTransaction debitAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException, ParkingException;

    List<AdvantageTransaction> debitAllAdvantage(Customer customer, List<AdvantageItem> items) throws CustomerDoesntHaveAdvantageException, ParkingException;

}