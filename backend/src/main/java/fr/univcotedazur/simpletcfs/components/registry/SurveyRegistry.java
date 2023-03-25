package fr.univcotedazur.simpletcfs.components.registry;

import fr.univcotedazur.simpletcfs.entities.Answer;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.interfaces.SurveyFinder;
import fr.univcotedazur.simpletcfs.interfaces.SurveyModifier;
import fr.univcotedazur.simpletcfs.repositories.AnswerRepository;
import fr.univcotedazur.simpletcfs.repositories.QuestionRepository;
import fr.univcotedazur.simpletcfs.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class SurveyRegistry implements SurveyFinder, SurveyModifier {
    SurveyRepository surveyRepository;
    QuestionRepository questionRepository;
    AnswerRepository answerRepository;

    @Autowired
    public SurveyRegistry(SurveyRepository surveyRepository, QuestionRepository questionRepository, AnswerRepository answerRepository){
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Survey newSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    // TODO : Je comprends pas pourquoi tu as besoin de cette méthode pour créer un survey vide ?
    @Override
    public Survey newSurvey() {
        Survey survey = new Survey();
        surveyRepository.save(survey);
        return survey;
    }

    @Override
    public Survey addQuestion(Long surveyID, Question question) {
        Survey survey = surveyRepository.findById(surveyID).orElse(null);
        if(survey != null){
            survey.getQuestions().add(question);
            surveyRepository.save(survey);
        }
        return survey;

    }

    @Override
    public Survey addAnswer(String answer, Long questionId, Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        Question question = questionRepository.findById(questionId).orElse(null);
        if (survey != null && question != null) {
            List<Question> questions = survey.getQuestions();
            for (Question q : questions) {
                if (q.getId().equals(questionId)) {
                    List<Answer> tmp = q.getAnswers();
                    Answer newAnswer = new Answer(answer, question);
                    answerRepository.save(newAnswer);
                    tmp.add(newAnswer);
                    q.setAnswers(tmp);
                }
            }
            surveyRepository.save(survey);
        }
        return survey;
    }

    @Override
    public Survey removeQuestion(Long question, Long surveyID) {
        Survey survey = surveyRepository.findById(surveyID).orElse(null);
        if (survey != null) {
            List<Question> questions = survey.getQuestions();
            for (Question q : questions) {
                if (q.getId().equals(question)) {
                    questions.remove(q);
                }
            }
            surveyRepository.save(survey);
        }
        return survey;
    }

    @Override
    public Optional<Survey> findById(Long id) {
        return surveyRepository.findById(id);
    }

    @Override
    public Iterable<Survey> findAll() {
        return surveyRepository.findAll();
    }
}
