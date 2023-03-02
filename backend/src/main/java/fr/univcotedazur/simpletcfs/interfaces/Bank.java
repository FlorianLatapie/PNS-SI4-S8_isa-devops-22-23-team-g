package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Euro;

public interface Bank {
    boolean pay(String creditCard,Euro price);
}
