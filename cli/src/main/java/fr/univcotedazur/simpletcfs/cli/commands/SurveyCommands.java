package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.model.survey.SurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class SurveyCommands {

    public static final String BASE_URI = "/surveys/";

    @Autowired
    RestTemplate restTemplate;

    @ShellMethod("On va voir")
    public SurveyDTO getSurveys() {
        return restTemplate.getForObject(BASE_URI, SurveyDTO.class);
    }
}
