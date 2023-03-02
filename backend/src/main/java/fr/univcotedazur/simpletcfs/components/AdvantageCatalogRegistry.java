package fr.univcotedazur.simpletcfs.components;


import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Status;
import fr.univcotedazur.simpletcfs.entities.Point;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageFinder;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageModifier;
import fr.univcotedazur.simpletcfs.repositories.AdvantageCatalogRepository;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public AdvantageItem findAnAdvantage(UUID id) {
        Optional<AdvantageItem> resAdvantage = this.advantageRepository.findById(id);
        return resAdvantage.orElse(null);
    }

    @Override
    public void addNewAdvantage(String title, Point amount, Shop shop, String description, Status type) {
        AdvantageItem advantageItem = new AdvantageItem(type,title,description,amount,shop);
        this.advantageRepository.save(advantageItem, advantageItem.getId());
    }

    @Override
    public AdvantageItem updateAdvantage(AdvantageItem advantageItem) {
        this.advantageRepository.save(advantageItem, advantageItem.getId());
        return advantageRepository.findById(advantageItem.getId()).orElse(null);
    }
}