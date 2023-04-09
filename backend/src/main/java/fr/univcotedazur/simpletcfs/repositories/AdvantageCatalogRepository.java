package fr.univcotedazur.simpletcfs.repositories;


import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvantageCatalogRepository extends JpaRepository<AdvantageItem,Long> {

    Optional<AdvantageItem> findByTitle(String name);
}
