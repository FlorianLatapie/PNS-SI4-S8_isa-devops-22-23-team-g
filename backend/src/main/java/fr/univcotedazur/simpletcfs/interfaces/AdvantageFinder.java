package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;
import java.util.UUID;

public interface AdvantageFinder {
    List<AdvantageItem> findAllAdvantages(Shop shop);
    AdvantageItem findAnAdvantage(UUID id);
}

