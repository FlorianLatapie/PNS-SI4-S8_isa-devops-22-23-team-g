package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.Customer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomerRepository extends BasicRepositoryImpl<Customer, UUID> {
}
