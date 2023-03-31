package fr.univcotedazur.simpletcfs.components.advantages;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.entities.Status;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import fr.univcotedazur.simpletcfs.interfaces.StatusModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class StatusUpdater implements StatusModifier {

    private final CustomerModifier modifier;
    private final CustomerFinder finder;

    @Autowired
    StatusUpdater(CustomerModifier modifier, CustomerFinder finder) {
        this.modifier = modifier;
        this.finder = finder;
    }


    //TODO: update the status of the customer by verifying the date of last transaction : < 7 days => VFP, > 7 days => CLASSIC
    public void updateStatus(Customer customer, EuroTransaction transaction) {
        var thisTransactionDate = transaction.getDate();

        updateStatus(customer, thisTransactionDate);

        customer.setLastEuroTransactionDate(thisTransactionDate);
    }

    void updateStatus(Customer customer, Date date) {
        var lastTransactionDate = customer.getLastEuroTransactionDate();
        var sevenDaysInMs = 7 * 24 * 60 * 60 * 1000;


        if (lastTransactionDate == null || Objects.equals(lastTransactionDate, new Date(0)) || lastTransactionDate.getTime() == 0) {
            customer.setStatus(Status.CLASSIC);
        } else if (date.getTime() - lastTransactionDate.getTime() < sevenDaysInMs) {
            customer.setStatus(Status.VFP);
        } else {
            customer.setStatus(Status.CLASSIC);
        }
    }
}
