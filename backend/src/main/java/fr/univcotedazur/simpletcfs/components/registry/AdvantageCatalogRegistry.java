package fr.univcotedazur.simpletcfs.components.registry;


import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Point;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.Status;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageFinder;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageModifier;
import fr.univcotedazur.simpletcfs.repositories.AdvantageCatalogRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdvantageCatalogRegistry implements AdvantageFinder, AdvantageModifier {

    AdvantageCatalogRepository advantageRepository;

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

    @Override
    public AdvantageItem findAnAdvantage(Long id) {
        return this.advantageRepository.findById(id).orElse(null);
    }

    @Override
    public void addNewAdvantage(String title, Point amount, Shop shop, String description, Status type) {
        AdvantageItem advantageItem = new AdvantageItem(type, title, description, amount, shop);
        this.advantageRepository.save(advantageItem);
    }

    @Override
    public AdvantageItem updateAdvantage(AdvantageItem advantageItem) {
        this.advantageRepository.save(advantageItem);
        return advantageRepository.findById(advantageItem.getId()).orElse(null);
    }
}
