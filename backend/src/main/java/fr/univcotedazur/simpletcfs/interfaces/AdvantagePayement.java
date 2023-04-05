package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.AdvantageTransaction;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.exceptions.ParkingException;

import java.util.List;

public interface AdvantagePayement {
    AdvantageTransaction debitAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException, ParkingException;

    void debitAllAdvantage(Customer customer, List<AdvantageItem> items) throws CustomerDoesntHaveAdvantageException, ParkingException;

}