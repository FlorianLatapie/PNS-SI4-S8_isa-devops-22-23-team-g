package fr.univcotedazur.simpletcfs.repositories;
import fr.univcotedazur.simpletcfs.entities.AdvantageTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdvantageTransactionRepository extends JpaRepository<AdvantageTransaction, Long> {
}
