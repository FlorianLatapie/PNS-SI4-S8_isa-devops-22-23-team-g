package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {

    Optional<Customer> findByName(String name);

    Optional<Customer> findById(UUID id);

}
