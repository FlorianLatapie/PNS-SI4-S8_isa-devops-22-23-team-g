package fr.univcotedazur.simpletcfs.repositories;


import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvantageCatalogRepository extends JpaRepository<AdvantageItem,Long> {}
