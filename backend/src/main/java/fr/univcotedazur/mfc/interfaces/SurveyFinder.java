package fr.univcotedazur.mfc.interfaces;

import java.util.Optional;

import fr.univcotedazur.mfc.entities.Survey;

public interface SurveyFinder {

    Optional<Survey> findById(Long id);

    Iterable<Survey> findAll();
}
