package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.PointTransaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PointTransactionRepository extends BasicRepositoryImpl<PointTransaction, UUID>{}
