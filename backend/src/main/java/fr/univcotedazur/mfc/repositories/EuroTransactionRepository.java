package fr.univcotedazur.mfc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univcotedazur.mfc.entities.EuroTransaction;

@Repository
public interface EuroTransactionRepository extends JpaRepository<EuroTransaction, Long> {}
