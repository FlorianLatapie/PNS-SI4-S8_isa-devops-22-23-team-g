package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.*;


public interface AdvantageModifier {
    AdvantageItem addNewAdvantage(String title, Point amount, Shop shop, String description, Status type);
    AdvantageItem updateAdvantage(AdvantageItem advantageItem);
}

