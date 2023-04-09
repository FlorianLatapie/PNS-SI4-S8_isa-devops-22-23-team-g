package fr.univcotedazur.mfc.time;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeController implements TimeProvider{
    @Override
    public Date now() {
        return new Date();
    }
}
