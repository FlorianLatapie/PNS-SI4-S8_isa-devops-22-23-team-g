package fr.univcotedazur.mfc.interfaces;

import java.sql.Timestamp;

public interface Park {
    boolean parkForFree(String licensePlate);
    boolean parkForDuration(String licensePlate, Timestamp duration);


}