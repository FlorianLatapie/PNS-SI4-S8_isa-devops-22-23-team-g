package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.Shop;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ShopRepository extends BasicRepositoryImpl<Shop, UUID> {}
