package fr.univcotedazur.simpletcfs.cli;

import fr.univcotedazur.simpletcfs.cli.model.CliCustomer;
import org.springframework.stereotype.Component;
import fr.univcotedazur.simpletcfs.cli.model.AdvantageItemDTO;
import fr.univcotedazur.simpletcfs.cli.model.AdvantageTransactionDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CliContext {

    private final Map<String, CliCustomer> customers;
    private Map<String, AdvantageItemDTO> advantageItems;

    public CliContext() {
        customers = new HashMap<>();
        advantageItems = new HashMap<>();
    }

    public Map<String, CliCustomer> getCustomers() {
        return customers;
    }

    public Map<String, AdvantageItemDTO> getAdvantageItems() {
        return advantageItems;
    }

    @Override
    public String toString() {
        return customers.keySet().stream()
                .map(key -> key + "=" + customers.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
