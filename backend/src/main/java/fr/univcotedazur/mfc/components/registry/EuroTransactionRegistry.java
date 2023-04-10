package fr.univcotedazur.mfc.components.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.EuroTransaction;
import fr.univcotedazur.mfc.entities.Shop;
import fr.univcotedazur.mfc.interfaces.EuroTransactionFinder;
import fr.univcotedazur.mfc.interfaces.EuroTransactionModifier;
import fr.univcotedazur.mfc.repositories.EuroTransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class EuroTransactionRegistry implements EuroTransactionFinder, EuroTransactionModifier {


    EuroTransactionRepository euroTransactionRepository;

    @Autowired
    public EuroTransactionRegistry(EuroTransactionRepository euroTransactionRepository) {
        this.euroTransactionRepository = euroTransactionRepository;
    }

    @Override
    public EuroTransaction find(Long id) {
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
        return euroTransactionRepository.save(transaction);
    }

    @Override
    public List<EuroTransaction> findAll() {
        return StreamSupport.stream(euroTransactionRepository.findAll().spliterator(), false).toList();
    }
}
