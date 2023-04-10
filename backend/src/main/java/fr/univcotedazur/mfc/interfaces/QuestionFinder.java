package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Question;

public interface QuestionFinder {
    Question findById(Long id);
}
