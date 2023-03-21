package fr.univcotedazur.simpletcfs.components.advantages;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;
import fr.univcotedazur.simpletcfs.interfaces.PointPayement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangePoint implements PointPayement {
    private final AdvantageManager advantageManager;

    @Autowired
    public ExchangePoint(AdvantageManager advantageManager) {
        this.advantageManager = advantageManager;
    }

    @Override
    // The advantage item is valid because it's given by the Controller
    public AdvantageItem payPoints(Customer customer, AdvantageItem item) throws NegativePointBalanceException {
        customer.getCustomerBalance().removePoint(item.getPrice());
        // need to add the advantage items
        advantageManager.addAdvantage(customer, item);
        return item;
    }
}
