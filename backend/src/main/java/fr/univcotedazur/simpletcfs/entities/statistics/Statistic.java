package fr.univcotedazur.simpletcfs.entities.statistics;

import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.ArrayList;
import java.util.List;

public class Statistic {

    private final List<StatisticValue> statisticValues = new ArrayList<>();
    private final Shop shop;

    public Statistic(double statisticValues, Indicator indicator, Shop shop) {
        this.statisticValues.add(new StatisticValueEuro("Money Earned", new Euro(statisticValues), indicator));
        this.shop = shop;
    }

    // TODO : to change later
    public Statistic(double statisticValues) {
       this(statisticValues, Indicator.AVERAGE, null);
    }

    public List<StatisticValue> getStatisticValues() {
        return statisticValues;
    }

    public Shop getShop() {
        return shop;
    }
}
