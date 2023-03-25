package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Question;

public interface QuestionFinder {
    Question findById(Long id);
}
