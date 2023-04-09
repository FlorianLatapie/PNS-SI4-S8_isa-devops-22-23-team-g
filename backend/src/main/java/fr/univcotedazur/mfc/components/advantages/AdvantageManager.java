package fr.univcotedazur.mfc.components.advantages;

import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.AdvantageItem;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.mfc.interfaces.AdvantageAdder;
import fr.univcotedazur.mfc.interfaces.AdvantageRemover;

import javax.transaction.Transactional;

@Component
@Transactional
public class AdvantageManager implements AdvantageAdder, AdvantageRemover   {

    @Override
    public void addAdvantage(Customer customer, AdvantageItem item) {
        customer.getAdvantageItems().add(item);
    }

    @Override
    public void removeAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException {
        if(!customer.getAdvantageItems().contains(item)){
            throw new CustomerDoesntHaveAdvantageException();
        }
        customer.getAdvantageItems().remove(item);
    }

    public void checkAdvantagePresence(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException {
        if (!customer.getAdvantageItems().contains(item)) {
            throw new CustomerDoesntHaveAdvantageException();
        }
    }
}
