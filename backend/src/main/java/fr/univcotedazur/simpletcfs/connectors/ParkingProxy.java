
package fr.univcotedazur.simpletcfs.connectors;

import fr.univcotedazur.simpletcfs.interfaces.Park;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ParkingProxy implements Park {
    @Override
    public boolean parkForFree(String licensePlate, Date startTime, Date endTime) {
        return true;
        // TODO Auto-generated method stub
    }
    
    @Override
    public boolean parkForDuration(String licensePlate, Date startTime, int minutesDuration) {
        return true;
        // TODO Auto-generated method stub
    }
}