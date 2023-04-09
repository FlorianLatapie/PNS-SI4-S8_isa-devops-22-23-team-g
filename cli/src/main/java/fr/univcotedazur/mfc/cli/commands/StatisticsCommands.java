package fr.univcotedazur.mfc.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import fr.univcotedazur.mfc.cli.model.statistic.StatisticDTO;

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
