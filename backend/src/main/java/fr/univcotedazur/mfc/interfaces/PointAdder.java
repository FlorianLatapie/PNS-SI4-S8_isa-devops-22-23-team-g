package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Euro;
import fr.univcotedazur.mfc.entities.Point;


public interface PointAdder {
    Point gain(Customer customer, Euro amount);
}
