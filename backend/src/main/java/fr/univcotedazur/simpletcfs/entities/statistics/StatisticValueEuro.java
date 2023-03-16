package fr.univcotedazur.simpletcfs.entities.statistics;

import fr.univcotedazur.simpletcfs.entities.Euro;

public class StatisticValueEuro extends StatisticValue {

    private final Euro value;

    public StatisticValueEuro(String description, Euro value, Indicator indicator) {
        super(description, indicator);
        this.value = value;
    }

    public Euro getValueEuro() {
        return value;
    }

    public double getValue() {
        return value.getEuro();
    }
}
