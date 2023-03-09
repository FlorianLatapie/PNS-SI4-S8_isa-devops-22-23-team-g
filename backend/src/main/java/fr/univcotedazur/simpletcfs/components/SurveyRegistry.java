package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Answer;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.interfaces.SurveyFinder;
import fr.univcotedazur.simpletcfs.interfaces.SurveyModifier;
import fr.univcotedazur.simpletcfs.repositories.SurveyRepository;

import java.util.List;
import java.util.UUID;

public class SurveyRegistry implements SurveyFinder, SurveyModifier {
    SurveyRepository surveyRepository;

    public SurveyRegistry(SurveyRepository surveyRepository){
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey newSurvey(Survey survey) {
        surveyRepository.save(survey,survey.getId());
        return survey;
    }

    @Override
    public Survey newSurvey() {
        Survey survey = new Survey();
        surveyRepository.save(survey,survey.getId());
        return survey;
    }

    @Override
    public Survey addQuestion(UUID surveyID, Question question) {
        Survey survey = surveyRepository.findById(surveyID).orElse(null);
        if(survey != null){
            survey.getQuestions().add(question);
            surveyRepository.save(survey,survey.getId());
        }
        return survey;

    }

    @Override
    public Survey addAnswer(String answer, UUID question, UUID surveyID) {
        Survey survey = surveyRepository.findById(surveyID).orElse(null);
        if (survey != null) {
            List<Question> questions = survey.getQuestions();
            for (Question q : questions) {
                if (q.getId().equals(question)) {
                    List<Answer> tmp = q.getAnswers();
                    tmp.add(new Answer(answer, question));
                    q.setAnswers(tmp);
                }
            }
            surveyRepository.save(survey,survey.getId());
        }
        return survey;
    }

    @Override
    public Survey removeQuestion(UUID question, UUID surveyID) {
        Survey survey = surveyRepository.findById(surveyID).orElse(null);
        if (survey != null) {
            List<Question> questions = survey.getQuestions();
            for (Question q : questions) {
                if (q.getId().equals(question)) {
                    questions.remove(q);
                }
            }
            surveyRepository.save(survey,survey.getId());
        }
        return survey;
    }

    @Override
    public Survey findById(UUID id) {
        return surveyRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Survey> findAll() {
        return surveyRepository.findAll();
    }
}
