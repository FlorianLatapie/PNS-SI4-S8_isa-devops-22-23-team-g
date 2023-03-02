package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.interfaces.EuroTransactionFinder;
import fr.univcotedazur.simpletcfs.interfaces.EuroTransactionModifier;
import fr.univcotedazur.simpletcfs.repositories.EuroTransactionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EuroTransactionRegistry implements EuroTransactionFinder, EuroTransactionModifier {
    EuroTransactionRepository euroTransactionRepository;
    @Override
    public EuroTransaction find(UUID id) {
        return euroTransactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<EuroTransaction> find(Shop shop) {
        List<EuroTransaction> transactions = new ArrayList<>();
        euroTransactionRepository.findAll().forEach(
                transaction -> {
                    if (transaction.getShop().equals(shop)) {
                        transactions.add(transaction);
                    }
                }
        );
        return transactions;
    }

    @Override
    public List<EuroTransaction> find(Customer customer) {
        List<EuroTransaction> transactions = new ArrayList<>();
        euroTransactionRepository.findAll().forEach(
                transaction -> {
                    if (transaction.getCustomer().equals(customer)) {
                        transactions.add(transaction);
                    }
                }
        );
        return transactions;
    }

    @Override
    public EuroTransaction add(EuroTransaction transaction) {
        euroTransactionRepository.save(transaction,transaction.getId());
        return transaction;
    }
}
