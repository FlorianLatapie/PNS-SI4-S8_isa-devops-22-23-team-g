package fr.univcotedazur.mfc.components.advantages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.*;
import fr.univcotedazur.mfc.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.mfc.exceptions.ParkingException;
import fr.univcotedazur.mfc.interfaces.AdvantagePayement;
import fr.univcotedazur.mfc.interfaces.AdvantageRemover;
import fr.univcotedazur.mfc.interfaces.Park;

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
