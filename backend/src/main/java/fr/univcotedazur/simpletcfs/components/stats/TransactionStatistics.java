package fr.univcotedazur.simpletcfs.components.stats;

import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.entities.statistics.Statistic;
import fr.univcotedazur.simpletcfs.interfaces.EuroTransactionFinder;
import fr.univcotedazur.simpletcfs.interfaces.StatsFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Component
public class TransactionStatistics implements StatsFinder {

    @Autowired
    private EuroTransactionFinder euroTransactionFinder;

    @Override
    public Statistic computeStatsAllShop() {
        List<EuroTransaction> euroTransactionList = euroTransactionFinder.findAll();
        double moneyEarned = 0;
        for (EuroTransaction euroTransaction : euroTransactionList) {
            moneyEarned += euroTransaction.getPrice().euroAmount();
        }
        return new Statistic(moneyEarned);
    }
}
