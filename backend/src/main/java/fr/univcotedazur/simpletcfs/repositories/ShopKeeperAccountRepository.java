package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.Account;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ShopKeeperAccountRepository extends BasicRepositoryImpl<Account, UUID> {}
