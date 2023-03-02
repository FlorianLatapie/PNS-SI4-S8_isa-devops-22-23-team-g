package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.AdvantageNotInBalanceException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageAdder;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageRemover;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class AdvantageManager implements AdvantageAdder, AdvantageRemover   {

    private final CustomerRepository customerRepository;


    public AdvantageManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addAdvantage(Customer customer, AdvantageItem item) {
        customer.getAdvantageItems().add(item);
        customerRepository.save(customer, customer.getId());
    }


    @Override
    public void removeAdvantage(Customer customer, AdvantageItem item) throws AdvantageNotInBalanceException {
        if(!customer.getAdvantageItems().contains(item))
            throw new AdvantageNotInBalanceException();
        customer.getAdvantageItems().remove(item);
        customerRepository.save(customer, customer.getId());
    }
}
