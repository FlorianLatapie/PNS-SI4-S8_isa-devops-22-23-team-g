package fr.univcotedazur.simpletcfs.components.advantages;
import fr.univcotedazur.simpletcfs.connectors.ParkingProxy;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.exceptions.ParkingException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantagePayement;
import fr.univcotedazur.simpletcfs.interfaces.Park;
import fr.univcotedazur.simpletcfs.repositories.AdvantageTransactionRepository;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

import java.util.List;

@Component
@Transactional
public class AdvantageCashier implements AdvantagePayement {

    private final AdvantageManager advantageManager; 
    private final AdvantageTransactionRepository advantageTransactionRepository;


    private Park parkingProxy = new ParkingProxy();

    public AdvantageCashier(AdvantageManager advantageManager, AdvantageTransactionRepository advantageTransactionRepository) {
        this.advantageManager = advantageManager;
        this.advantageTransactionRepository = advantageTransactionRepository;
    }

    public AdvantageTransaction debitAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException, ParkingException {
        if (!customer.getAdvantageItems().contains(item)) {
            throw new CustomerDoesntHaveAdvantageException();
        }
        boolean isParking = item.getTitle().equals("Parking");

        if(isParking && !parkingProxy.parkForFree(customer.getLicensePlate())){
            throw new ParkingException();
        }

        advantageManager.removeAdvantage(customer, item);
        return new AdvantageTransaction(new Date(), customer, item.getTitle());
    }

    public void debitAllAdvantage(Customer customer, List<AdvantageItem> items) throws CustomerDoesntHaveAdvantageException, ParkingException {
        for (AdvantageItem item : items) {
            debitAdvantage(customer, item);
        }
    }

}
