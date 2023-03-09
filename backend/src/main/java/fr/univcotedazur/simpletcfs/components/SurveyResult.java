package fr.univcotedazur.simpletcfs.components;


import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.repositories.SurveyRepository;

import java.util.UUID;

public class SurveyResult {

    private SurveyRepository surveyRepository;

    public SurveyResult(SurveyRepository surveyRepository){
        this.surveyRepository = surveyRepository;
    }

    public void getSurveyResultPrint(UUID surveyID){
        Survey survey= surveyRepository.findById(surveyID).orElse(null);
        if (survey != null) {
            survey.getQuestions().forEach(question -> {
                System.out.println(question.getQuestionContent());
                question.getAnswers().forEach(answer -> {
                    System.out.println(answer.getAnswer());
                });
            });
        }
    }

    public Survey getSurveyResult(UUID surveyID){
        return surveyRepository.findById(surveyID).orElse(null);
    }

   public int getNbParticipants(UUID surveyID){
        Survey survey= surveyRepository.findById(surveyID).orElse(null);
        if (survey != null) {
            return survey.getParticipants().size();
        }
        return 0;
    }
}
