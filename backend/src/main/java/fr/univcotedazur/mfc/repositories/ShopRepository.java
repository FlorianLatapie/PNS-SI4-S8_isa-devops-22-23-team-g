package fr.univcotedazur.mfc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univcotedazur.mfc.entities.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {}
