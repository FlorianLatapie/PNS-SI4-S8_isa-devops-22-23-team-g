package fr.univcotedazur.mfc.cli;

import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.cli.model.AdvantageItemDTO;
import fr.univcotedazur.mfc.cli.model.CliCustomer;

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
