package fr.univcotedazur.simpletcfs.components.registry;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.PointTransaction;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.interfaces.PointTransactionFinder;
import fr.univcotedazur.simpletcfs.interfaces.PointTransactionModifier;
import fr.univcotedazur.simpletcfs.repositories.PointTransactionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PointTransactionRegistry implements PointTransactionFinder, PointTransactionModifier {

    PointTransactionRepository pointTransactionRepository;

    @Override
    public PointTransaction findPoint(Long id) {
        Optional<PointTransaction> pointTransaction = pointTransactionRepository.findById(id);
        return pointTransaction.orElse(null);
    }

    // TODO: implement this method
    @Override
    public List<PointTransaction> findPoint(Shop shop) {
        return null;
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
