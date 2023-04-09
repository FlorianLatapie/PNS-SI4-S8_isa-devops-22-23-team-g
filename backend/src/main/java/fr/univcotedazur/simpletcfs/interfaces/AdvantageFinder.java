package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;
import java.util.Optional;

public interface AdvantageFinder {
    List<AdvantageItem> findAllAdvantages(Shop shop);
    List<AdvantageItem> findAllAdvantages();
    AdvantageItem findAnAdvantage(Long id);

    Optional<AdvantageItem> findByTitle(String name);
}

