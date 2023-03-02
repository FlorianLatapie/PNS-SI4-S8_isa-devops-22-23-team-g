package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.AdvantageNotInBalanceException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantagePayement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvantageCashier implements AdvantagePayement {

    private final AdvantageManager advantageManager;

    public AdvantageCashier(AdvantageManager advantageManager) {
        this.advantageManager = advantageManager;
    }
    @Override
    public void debitAdvantage(Customer customer, AdvantageItem item) throws AdvantageNotInBalanceException {
//        customer.getAdvantageItems().remove(item);
        debitAdvantage(customer, List.of(item));
    }

    @Override
    public void debitAdvantage(Customer customer, List<AdvantageItem> items) throws AdvantageNotInBalanceException {
        for (AdvantageItem item : items) {
            try{
                advantageManager.removeAdvantage(customer, item);
//                customer.getAdvantageItems().remove(item);
            }catch (Exception e){
                throw new AdvantageNotInBalanceException();
            }
        }

    }
}
