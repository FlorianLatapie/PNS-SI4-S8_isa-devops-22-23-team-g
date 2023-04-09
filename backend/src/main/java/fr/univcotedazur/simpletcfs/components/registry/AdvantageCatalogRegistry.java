package fr.univcotedazur.simpletcfs.components.registry;


import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Point;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.Status;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageFinder;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageModifier;
import fr.univcotedazur.simpletcfs.repositories.AdvantageCatalogRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AdvantageCatalogRegistry implements AdvantageFinder, AdvantageModifier {

    private final AdvantageCatalogRepository advantageRepository;

   @Autowired
    public AdvantageCatalogRegistry(AdvantageCatalogRepository advantageRepository) {
        this.advantageRepository = advantageRepository;
        this.addNewAdvantage( "Croissant", new Point(20), null, "un délicieux croissant, croquant, gourmand" , Status.CLASSIC);
        this.addNewAdvantage( "Chocolatine", new Point(30), null,  "une chocoloatine fondante", Status.CLASSIC);
        this.addNewAdvantage( "Café", new Point(10),null,  "un café bien chaud", Status.CLASSIC);
        this.addNewAdvantage( "Thé", new Point(10), null,  "un thé vert aromatisé", Status.CLASSIC);
        this.addNewAdvantage( "Jus", new Point(10), null,  "un jus de fruit frais",Status.CLASSIC);
        this.addNewAdvantage( "Parking", new Point(20), null,  "un ticket pour le parking", Status.VFP);
    }

    @Override
    public List<AdvantageItem> findAllAdvantages(Shop shop) {
        List<AdvantageItem> advantageItems = new ArrayList<>();
        advantageRepository.findAll().forEach(advantageItem -> {
            if (advantageItem.getShop() == shop) {
                advantageItems.add(advantageItem);
            }
        });
        return advantageItems;
    }

    public List<AdvantageItem> findAllAdvantages() {
        List<AdvantageItem> advantageItems = new ArrayList<>();
        advantageRepository.findAll().forEach(advantageItems::add);
        return advantageItems;
    }

    @Override
    public AdvantageItem findAnAdvantage(Long id) {
        return this.advantageRepository.findById(id).orElse(null);
    }

    @Override
    public AdvantageItem addNewAdvantage(String title, Point amount, Shop shop, String description, Status type) {
        AdvantageItem advantageItem = new AdvantageItem(type, title, description, amount, shop);
        this.advantageRepository.save(advantageItem);
        return advantageItem;
    }

    @Override
    public AdvantageItem updateAdvantage(AdvantageItem advantageItem) {
        this.advantageRepository.save(advantageItem);
        return advantageRepository.findById(advantageItem.getId()).orElse(null);
    }

    @Override
    public Optional<AdvantageItem> findByTitle(String name) {
        return advantageRepository.findByTitle(name);
    }
}
