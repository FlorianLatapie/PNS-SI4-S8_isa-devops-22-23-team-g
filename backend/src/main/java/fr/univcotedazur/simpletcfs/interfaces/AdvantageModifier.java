package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.*;


public interface AdvantageModifier {
    void addNewAdvantage(String title, Point amount, Shop shop, String description, Status type);
    AdvantageItem updateAdvantage(AdvantageItem advantageItem);


}

