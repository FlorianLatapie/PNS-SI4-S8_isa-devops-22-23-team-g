package fr.univcotedazur.mfc.entities.statistics;

import fr.univcotedazur.mfc.entities.Euro;

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
        return value.euroAmount();
    }

    @Override
    public String toString() {
        return "StatisticValueEuro{" +
                "value=" + value +
                '}';
    }
}
