package fr.univcotedazur.mfc.controllers.dto.statistic;

public class StatisticValueDTO {

    private final double value;
    private final String description;
    private final String indicator;

    public StatisticValueDTO(double value, String description, String indicator) {
        this.value = value;
        this.description = description;
        this.indicator = indicator;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getIndicator() {
        return indicator;
    }
}
