package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.statistics.Statistic;

public interface StatsFinder {

    Statistic computeStatsAllShop();

    Statistic computeStatsForShop(Shop shop);
}
