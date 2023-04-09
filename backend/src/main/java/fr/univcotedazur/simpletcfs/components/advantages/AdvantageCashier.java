package fr.univcotedazur.simpletcfs.components.advantages;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.exceptions.ParkingException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantagePayement;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageRemover;
import fr.univcotedazur.simpletcfs.interfaces.Park;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

@Component
@Transactional
public class AdvantageCashier implements AdvantagePayement {

    private final AdvantageRemover advantageRemover;

    private final Park parkingProxy;

    @Autowired
    public AdvantageCashier(AdvantageRemover advantageRemover, Park parkingProxy) {
        this.advantageRemover = advantageRemover;
        this.parkingProxy = parkingProxy;
    }

    public AdvantageTransaction debitAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException, ParkingException {
        advantageRemover.checkAdvantagePresence(customer, item);
        boolean isParking = item.getTitle().equals("Parking");

        if(isParking && !parkingProxy.parkForFree(customer.getLicensePlate())){
            throw new ParkingException();
        }

        advantageRemover.removeAdvantage(customer, item);
        return new AdvantageTransaction(new Date(), customer, item);
    }

    public List<AdvantageTransaction> debitAllAdvantage(Customer customer, List<AdvantageItem> items) throws CustomerDoesntHaveAdvantageException, ParkingException {
        List<AdvantageTransaction> transactions = new ArrayList<>();
        for (AdvantageItem item : items) {
            transactions.add(debitAdvantage(customer, item));
        }
        return transactions;
    }
}
