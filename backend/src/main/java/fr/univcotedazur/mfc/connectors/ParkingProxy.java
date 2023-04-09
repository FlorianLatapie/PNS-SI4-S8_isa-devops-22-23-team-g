package fr.univcotedazur.mfc.connectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fr.univcotedazur.mfc.connectors.externaldto.FreeParkingDTO;
import fr.univcotedazur.mfc.interfaces.Park;

import java.sql.Timestamp;

@Component
public class ParkingProxy implements Park {

    private String parkingHostAndPort = "http://parking-system-staging:9080";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean parkForFree(String licensePlate) {
        try {
            ResponseEntity<FreeParkingDTO> response = restTemplate.postForEntity(
                    parkingHostAndPort + "/parking",
                    new FreeParkingDTO(licensePlate),
                    FreeParkingDTO.class);
            return response.getStatusCode().equals(HttpStatus.CREATED);
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean parkForDuration(String licensePlate, Timestamp duration) {
        return true;
    }


}