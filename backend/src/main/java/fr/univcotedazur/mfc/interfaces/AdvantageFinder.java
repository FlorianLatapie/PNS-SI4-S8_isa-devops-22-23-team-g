package fr.univcotedazur.mfc.interfaces;

import java.util.List;
import java.util.Optional;

import fr.univcotedazur.mfc.entities.AdvantageItem;
import fr.univcotedazur.mfc.entities.Shop;

public interface AdvantageFinder {
    List<AdvantageItem> findAllAdvantages(Shop shop);
    List<AdvantageItem> findAllAdvantages();
    AdvantageItem findAnAdvantage(Long id);

    Optional<AdvantageItem> findByTitle(String name);
}

