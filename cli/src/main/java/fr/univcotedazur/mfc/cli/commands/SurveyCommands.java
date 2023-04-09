package fr.univcotedazur.mfc.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import fr.univcotedazur.mfc.cli.model.survey.SurveyDTO;

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
