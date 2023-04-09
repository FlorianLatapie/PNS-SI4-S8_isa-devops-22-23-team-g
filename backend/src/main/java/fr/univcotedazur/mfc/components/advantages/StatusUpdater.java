package fr.univcotedazur.mfc.components.advantages;

import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.EuroTransaction;
import fr.univcotedazur.mfc.entities.Status;
import fr.univcotedazur.mfc.interfaces.StatusModifier;

import java.util.Date;
import java.util.Objects;

@Component
public class StatusUpdater implements StatusModifier {

    public void updateStatus(Customer customer, EuroTransaction transaction) {
        Date thisTransactionDate = transaction.getDate();
        updateStatus(customer, thisTransactionDate);
        customer.setLastEuroTransactionDate(thisTransactionDate);
    }

    private void updateStatus(Customer customer, Date date) {
        Date lastTransactionDate = customer.getLastEuroTransactionDate();
        int sevenDaysInMs = 7 * 24 * 60 * 60 * 1000;

        if (lastTransactionDate == null || Objects.equals(lastTransactionDate, new Date(0)) || lastTransactionDate.getTime() == 0) {
            customer.setStatus(Status.CLASSIC);
        } else if (date.getTime() - lastTransactionDate.getTime() < sevenDaysInMs) {
            customer.setStatus(Status.VFP);
        } else {
            customer.setStatus(Status.CLASSIC);
        }
    }
}
