package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class AdvantageCommands {

    public static final String BASE_URI = "/advantage/";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;


    @ShellMethod("get the advantage catalog and save it in cli context")
    public ListAdvantageItemDTO getCatalog() {
        ResponseEntity<ListAdvantageItemDTO> response = restTemplate.getForEntity(BASE_URI + "catalog", ListAdvantageItemDTO.class);
        ListAdvantageItemDTO catalog = response.getBody();
        if(catalog == null) return null;
        for (AdvantageItemDTO advantageItemDTO : catalog.getAdvantageItemDTOs()) {
            cliContext.getAdvantageItems().put(advantageItemDTO.getTitle(), advantageItemDTO);
        }
        return catalog;
    }

    @ShellMethod("get customer advantages balance")
    public ListAdvantageItemDTO getCustomerAdvantages(String customerName) {

        Long customerID = cliContext.getCustomers().get(customerName).getId();

        ResponseEntity<ListAdvantageItemDTO> response = restTemplate.getForEntity(BASE_URI + customerID + "/getCustomerAdvantages", ListAdvantageItemDTO.class);
        return response.getBody();
    }


    @ShellMethod("debit an advantage from customer balance")
    public AdvantageTransactionDTO debitAdvantage(String advantageName,String customerName) {
        AdvantageItemDTO advantageItemDTO = cliContext.getAdvantageItems().get(advantageName);
        String advantageItemId = String.valueOf(advantageItemDTO.getId());
        Long customerID = cliContext.getCustomers().get(customerName).getId();
        return restTemplate.postForObject(BASE_URI + customerID + "/debitAdvantage/" + advantageItemId, new AdvantageTransactionDTO(new CliCustomer(customerName), advantageItemDTO), AdvantageTransactionDTO.class);
    }

    @ShellMethod("exchange points for an advantage")
    public PointTransactionDTO payPoints(String advantageName, String customerName) {
        AdvantageItemDTO advantageItemDTO = cliContext.getAdvantageItems().get(advantageName);

        String advantageItemId = String.valueOf(advantageItemDTO.getId());

        Long customerID = cliContext.getCustomers().get(customerName).getId();
        return restTemplate.postForObject(BASE_URI + customerID + "/payPoints/" + advantageItemId, new PointTransactionDTO(new CliCustomer(customerName),advantageName, advantageItemDTO.getPrice()), PointTransactionDTO.class);
    }


}