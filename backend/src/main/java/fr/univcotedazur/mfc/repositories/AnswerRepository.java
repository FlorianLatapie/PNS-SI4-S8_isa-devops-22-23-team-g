package fr.univcotedazur.mfc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univcotedazur.mfc.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
