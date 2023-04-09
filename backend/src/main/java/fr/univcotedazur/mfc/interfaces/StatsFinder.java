package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Shop;
import fr.univcotedazur.mfc.entities.statistics.Statistic;

public interface StatsFinder {

    Statistic computeStatsAllShop();

    Statistic computeStatsForShop(Shop shop);
}
