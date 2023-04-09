package fr.univcotedazur.mfc.components.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.EuroTransaction;
import fr.univcotedazur.mfc.entities.Shop;
import fr.univcotedazur.mfc.entities.statistics.Statistic;
import fr.univcotedazur.mfc.interfaces.EuroTransactionFinder;
import fr.univcotedazur.mfc.interfaces.PointTransactionFinder;
import fr.univcotedazur.mfc.interfaces.StatsFinder;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Component
public class TransactionStatistics implements StatsFinder {

    private final EuroTransactionFinder euroTransactionFinder;

    private final PointTransactionFinder pointTransactionFinder;

    @Autowired
    public TransactionStatistics(EuroTransactionFinder euroTransactionFinder, PointTransactionFinder pointTransactionFinder) {
        this.euroTransactionFinder = euroTransactionFinder;
        this.pointTransactionFinder = pointTransactionFinder;
    }

    @Override
    public Statistic computeStatsAllShop() {
        List<EuroTransaction> euroTransactionList = euroTransactionFinder.findAll();
        double moneyEarned = 0;
        for (EuroTransaction euroTransaction : euroTransactionList) {
            moneyEarned += euroTransaction.getPrice().euroAmount();
        }
        return new Statistic(moneyEarned);
    }

    @Override
    public Statistic computeStatsForShop(Shop shop) {
        return null;
    }
}
