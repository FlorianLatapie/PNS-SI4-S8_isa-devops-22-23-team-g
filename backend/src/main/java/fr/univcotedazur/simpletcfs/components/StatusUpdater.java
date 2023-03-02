package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusUpdater {

    private CustomerModifier modifier;
    private CustomerFinder finder;

    @Autowired
    StatusUpdater(CustomerModifier modifier, CustomerFinder finder) {
        this.modifier = modifier;
        this.finder = finder;
    }


    //TODO: update the status of the customer by verifying the date of last transaction : < 7 days => VFP, > 7 days => CLASSIC
    void UpdateStatus() {

    }
}
