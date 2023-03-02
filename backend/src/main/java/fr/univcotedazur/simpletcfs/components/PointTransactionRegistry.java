package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Point;
import fr.univcotedazur.simpletcfs.entities.PointTransaction;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.interfaces.PointTransactionFinder;
import fr.univcotedazur.simpletcfs.interfaces.PointTransactionModifier;
import fr.univcotedazur.simpletcfs.repositories.PointTransactionRepository;
import io.cucumber.java.ja.且つ;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PointTransactionRegistry implements PointTransactionFinder,PointTransactionModifier {

    PointTransactionRepository pointTransactionRepository;
    @Override
    public PointTransaction findPoint(UUID id) {
        Optional<PointTransaction> pointTransaction = pointTransactionRepository.findById(id);
        return pointTransaction.orElse(null);
    }

    @Override
    public List<PointTransaction> findPoint(Shop shop) {
        return null;
    }

    @Override
    public List<PointTransaction> findPoint(Customer customer) {
        List<PointTransaction> pointTransactions = new ArrayList<>();
        pointTransactionRepository.findAll().forEach(p -> {
            if(p.getCustomer().equals(customer)){
                pointTransactions.add(p);
            }
        });
        return pointTransactions;
    }

    @Override
    public void add(PointTransaction transaction) {
        pointTransactionRepository.save(transaction, transaction.getId());
    }
}
