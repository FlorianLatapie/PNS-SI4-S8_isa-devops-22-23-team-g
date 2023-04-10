package fr.univcotedazur.mfc.entities.statistics;

public enum Indicator {
    ABOVE_AVERAGE("Above average"), BELOW_AVERAGE("Below average"), AVERAGE("Average");

    private final String description;

    Indicator(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
