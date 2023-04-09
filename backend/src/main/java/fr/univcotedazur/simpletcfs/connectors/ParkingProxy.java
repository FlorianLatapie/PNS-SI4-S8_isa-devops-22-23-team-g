package fr.univcotedazur.simpletcfs.connectors;

import fr.univcotedazur.simpletcfs.connectors.externaldto.FreeParkingDTO;
import fr.univcotedazur.simpletcfs.interfaces.Park;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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