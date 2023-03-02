package fr.univcotedazur.simpletcfs.connectors;

import fr.univcotedazur.simpletcfs.connectors.eternaldto.BankPaymentDTO;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.interfaces.Bank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class BankProxy implements Bank {
    @Value("${bank.host.baseurl}")
    private String bankHostandPort;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean pay(String creditCard, Euro price) { //pour ne pas avoir de bad request il faut include "896983" dans le numéro de carte
        try {
            ResponseEntity<BankPaymentDTO> result = restTemplate.postForEntity(
                    bankHostandPort + "/cctransactions",
                    new BankPaymentDTO(creditCard,(double)price.centsAmount()/100),
                    BankPaymentDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        }
        catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                System.out.println("Payment failed");
                return false;
            }
            throw errorException;
        }
    }
}
