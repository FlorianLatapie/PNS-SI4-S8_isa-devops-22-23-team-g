package fr.univcotedazur.mfc.components.advantages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.AdvantageItem;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.PointTransaction;
import fr.univcotedazur.mfc.exceptions.NegativePointBalanceException;
import fr.univcotedazur.mfc.interfaces.PointPayement;
import fr.univcotedazur.mfc.interfaces.PointTransactionModifier;

@Component
public class ExchangePoint implements PointPayement {
    private final AdvantageManager advantageManager;

    private final PointTransactionModifier pointTransactionModifier;

    @Autowired
    public ExchangePoint(AdvantageManager advantageManager, PointTransactionModifier pointTransactionModifier) {
        this.advantageManager = advantageManager;
        this.pointTransactionModifier = pointTransactionModifier;
    }

    @Override
    // The advantage item is valid because it's given by the Controller
    public PointTransaction payPoints(Customer customer, AdvantageItem item) throws NegativePointBalanceException {
        customer.getCustomerBalance().removePoint(item.getPrice());
        // need to add the advantage items
        advantageManager.addAdvantage(customer, item);
        PointTransaction pointTransaction = new PointTransaction(item.getPrice(), customer, item);
        pointTransactionModifier.add(pointTransaction);
        return pointTransaction;
    }
}
