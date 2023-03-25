package fr.univcotedazur.simpletcfs.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class TrackingCommands {

    @Autowired
    RestTemplate restTemplate;

    @ShellMethod("Track an order by id (track ORDER_ID)")
    public String track(Long id) {
        return restTemplate.getForObject("/orders/" + id, String.class);
    }

}
