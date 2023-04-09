package fr.univcotedazur.mfc.entities.statistics;

import java.util.ArrayList;
import java.util.List;

import fr.univcotedazur.mfc.entities.Euro;
import fr.univcotedazur.mfc.entities.Shop;

public class Statistic {

    private final List<StatisticValue> statisticValues = new ArrayList<>();
    private final Shop shop;

    public Statistic(double statisticValues, Indicator indicator, Shop shop) {
        this.statisticValues.add(new StatisticValueEuro("Money Earned", new Euro(statisticValues), indicator));
        this.shop = shop;
    }

    public Statistic(double statisticValues) {
       this(statisticValues, Indicator.AVERAGE, null);
    }

    public List<StatisticValue> getStatisticValues() {
        return statisticValues;
    }

    public Shop getShop() {
        return shop;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "statisticValues=" + statisticValues +
                ", shop=" + shop +
                '}';
    }
}
