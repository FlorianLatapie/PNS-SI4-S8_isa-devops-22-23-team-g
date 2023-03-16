package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.statistic.StatisticDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.statistic.StatisticValueDTO;
import fr.univcotedazur.simpletcfs.entities.statistics.Statistic;
import fr.univcotedazur.simpletcfs.entities.statistics.StatisticValue;
import fr.univcotedazur.simpletcfs.interfaces.StatsFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = StatsController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class StatsController {

        public static final String BASE_URI = "/stats";

        @Autowired
        private StatsFinder statsFinder;

        @GetMapping(path = "/all")
        public StatisticDTO computeStatsAllShop() {
            return convertStatisticToDto(statsFinder.computeStatsAllShop());
        }

        private StatisticDTO convertStatisticToDto(Statistic statistic) {
                String shopName = statistic.getShop() != null ? statistic.getShop().getName() : "All shops";
                List<StatisticValueDTO> statisticValueDTOS = statistic.getStatisticValues().stream().map(this::convertStatisticValueToDto).toList();
            return new StatisticDTO(shopName, statisticValueDTOS);
        }

        private StatisticValueDTO convertStatisticValueToDto(StatisticValue statisticValue) {
                String description = statisticValue.getDescription();
                String indicator = statisticValue.getIndicator().getDescription();
                double value = statisticValue.getValue();
            return new StatisticValueDTO(value, description, indicator);
        }
}
