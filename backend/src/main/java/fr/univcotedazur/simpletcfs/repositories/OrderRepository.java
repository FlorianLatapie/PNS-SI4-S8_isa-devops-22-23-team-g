package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.Order;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OrderRepository extends BasicRepositoryImpl<Order, UUID> {
}
