package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.model.statistic.StatisticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class StatisticsCommands {

    public static final String BASE_URI = "/stats/";

    @Autowired
    RestTemplate restTemplate;

    @ShellMethod("List all available recipes")
    public StatisticDTO getStatsFromAllShops() {
        return restTemplate.getForObject(BASE_URI + "all/", StatisticDTO.class);
    }
}
