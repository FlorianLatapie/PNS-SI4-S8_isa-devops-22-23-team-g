package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Survey;

import java.util.UUID;

public interface SurveyFinder {

    Survey findById(UUID id);

    Iterable<Survey> findAll();
}
