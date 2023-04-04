package fr.univcotedazur.simpletcfs.components.advantages;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageAdder;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageRemover;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AdvantageManager implements AdvantageAdder, AdvantageRemover   {

    private final CustomerRepository customerRepository;

    public AdvantageManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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
}
