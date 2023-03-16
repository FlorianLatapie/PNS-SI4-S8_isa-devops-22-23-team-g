package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.entities.statistics.Statistic;
import fr.univcotedazur.simpletcfs.interfaces.EuroTransactionFinder;
import fr.univcotedazur.simpletcfs.interfaces.StatsFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionStatistics implements StatsFinder {

    @Autowired
    private EuroTransactionFinder euroTransactionFinder;

    @Override
    public Statistic computeStatsAllShop() {
        List<EuroTransaction> euroTransactionList = euroTransactionFinder.findAll();
        double moneyEarned = 0;
        for (EuroTransaction euroTransaction : euroTransactionList) {
            moneyEarned += euroTransaction.getPrice().getEuro();
        }
        return new Statistic(moneyEarned);
    }
}
