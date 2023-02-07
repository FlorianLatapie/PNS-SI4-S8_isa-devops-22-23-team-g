package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.model.CookieEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ShellComponent
public class RecipeCommands {

    @Autowired
    RestTemplate restTemplate;

    @ShellMethod("List all available recipes")
    public Set<CookieEnum> recipes() {
        return new HashSet<CookieEnum>(Arrays.asList(restTemplate.getForObject("/recipes", CookieEnum[].class)));
    }

}
