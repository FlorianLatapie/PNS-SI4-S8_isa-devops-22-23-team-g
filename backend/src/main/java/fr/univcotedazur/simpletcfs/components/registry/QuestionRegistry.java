package fr.univcotedazur.simpletcfs.components.registry;

import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.interfaces.QuestionFinder;
import fr.univcotedazur.simpletcfs.interfaces.QuestionModifier;
import fr.univcotedazur.simpletcfs.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
