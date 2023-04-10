package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Euro;

public interface Bank {
    boolean pay(String creditCard,Euro price);
}
