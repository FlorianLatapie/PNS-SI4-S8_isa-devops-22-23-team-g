package fr.univcotedazur.mfc.components.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.Question;
import fr.univcotedazur.mfc.interfaces.QuestionFinder;
import fr.univcotedazur.mfc.interfaces.QuestionModifier;
import fr.univcotedazur.mfc.repositories.QuestionRepository;

@Component
public class QuestionRegistry implements QuestionFinder, QuestionModifier {
    private QuestionRepository repository;

    @Autowired
    public QuestionRegistry(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Question save(Question question) {
        return repository.save(question);
    }
}
