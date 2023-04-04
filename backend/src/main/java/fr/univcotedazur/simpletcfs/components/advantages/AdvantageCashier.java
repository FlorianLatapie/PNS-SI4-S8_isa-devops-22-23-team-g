package fr.univcotedazur.simpletcfs.components.advantages;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantagePayement;
import fr.univcotedazur.simpletcfs.repositories.AdvantageTransactionRepository;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

import java.util.List;

@Component
@Transactional
public class AdvantageCashier implements AdvantagePayement {

    private final AdvantageManager advantageManager; // object instread of interface ??
    private final AdvantageTransactionRepository advantageTransactionRepository;

    public AdvantageCashier(AdvantageManager advantageManager, AdvantageTransactionRepository advantageTransactionRepository) {
        this.advantageManager = advantageManager;
        this.advantageTransactionRepository = advantageTransactionRepository;
    }

    public AdvantageTransaction debitAdvantage(Customer customer, AdvantageItem item) throws CustomerDoesntHaveAdvantageException {
        if (!customer.getAdvantageItems().contains(item)) {
            throw new CustomerDoesntHaveAdvantageException();
        }
        advantageManager.removeAdvantage(customer, item);
        AdvantageTransaction advantageTransaction =  new AdvantageTransaction(new Date(), customer, item.getTitle());
        return advantageTransaction;
    }

    public void debitAllAdvantage(Customer customer, List<AdvantageItem> items) throws CustomerDoesntHaveAdvantageException {
        for (AdvantageItem item : items) {
            debitAdvantage(customer, item);
        }
    }
}
