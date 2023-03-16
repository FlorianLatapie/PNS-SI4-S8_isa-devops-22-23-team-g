package fr.univcotedazur.simpletcfs.entities.statistics;

public abstract class StatisticValue {
    protected Indicator indicator;
    protected String description;

    public StatisticValue(String description, Indicator indicator) {
        this.description = description;
        this.indicator = indicator;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public String getDescription() {
        return description;
    }

    public abstract double getValue();
}
