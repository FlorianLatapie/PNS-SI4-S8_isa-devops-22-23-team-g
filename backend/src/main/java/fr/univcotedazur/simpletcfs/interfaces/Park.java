package fr.univcotedazur.simpletcfs.interfaces;

import java.util.Date;

public interface Park {
    boolean parkForFree(String licensePlate, Date startTime, Date endTime); 
    boolean parkForDuration(String licensePlate, Date startTime, int minutesDuration);
}