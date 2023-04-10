package fr.univcotedazur.mfc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import fr.univcotedazur.mfc.entities.AdvantageItem;

import java.util.Optional;

public interface AdvantageCatalogRepository extends JpaRepository<AdvantageItem,Long> {

    Optional<AdvantageItem> findByTitle(String name);
}
