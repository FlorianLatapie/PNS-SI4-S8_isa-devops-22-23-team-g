package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;

public interface AdvantageFinder {
    List<AdvantageItem> findAllAdvantages(Shop shop);
    AdvantageItem findAnAdvantage(Long id);
}

