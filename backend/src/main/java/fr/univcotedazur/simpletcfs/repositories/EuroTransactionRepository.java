package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class EuroTransactionRepository extends BasicRepositoryImpl<EuroTransaction, UUID> {}
