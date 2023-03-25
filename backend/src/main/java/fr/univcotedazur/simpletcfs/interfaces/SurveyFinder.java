package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Survey;

import java.util.Optional;

public interface SurveyFinder {

    Optional<Survey> findById(Long id);

    Iterable<Survey> findAll();
}
