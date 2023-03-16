package fr.univcotedazur.simpletcfs.cli.model.statistic;

import java.util.List;

public class StatisticDTO {
    private final String shopName;
    private final List<StatisticValueDTO> values;

    public StatisticDTO(String shopName, List<StatisticValueDTO> values) {
        this.shopName = shopName;
        this.values = values;
    }

    public String getShopName() {
        return shopName;
    }

    public List<StatisticValueDTO> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "StatisticDTO{" +
                "shopName='" + shopName + '\'' +
                ", values=" + values +
                '}';
    }
}
