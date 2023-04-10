package fr.univcotedazur.mfc.components.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.PointTransaction;
import fr.univcotedazur.mfc.entities.Shop;
import fr.univcotedazur.mfc.interfaces.PointTransactionFinder;
import fr.univcotedazur.mfc.interfaces.PointTransactionModifier;
import fr.univcotedazur.mfc.repositories.PointTransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PointTransactionRegistry implements PointTransactionFinder, PointTransactionModifier {

    private final PointTransactionRepository pointTransactionRepository;

    @Autowired
    public PointTransactionRegistry(PointTransactionRepository pointTransactionRepository) {
        this.pointTransactionRepository = pointTransactionRepository;
    }

    @Override
    public PointTransaction findPoint(Long id) {
        Optional<PointTransaction> pointTransaction = pointTransactionRepository.findById(id);
        return pointTransaction.orElse(null);
    }

    @Override
    public List<PointTransaction> findPoint(Shop shop) {
        return new ArrayList<>();
    }

    @Override
    public List<PointTransaction> findPoint(Customer customer) {
        List<PointTransaction> pointTransactions = new ArrayList<>();
        pointTransactionRepository.findAll().forEach(
                pointTransaction -> {
                    if (pointTransaction.getCustomer().equals(customer)) {
                        pointTransactions.add(pointTransaction);
                    }
                });
        return pointTransactions;
    }

    @Override
    public void add(PointTransaction transaction) {
        pointTransactionRepository.save(transaction);
    }
}
