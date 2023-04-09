package fr.univcotedazur.mfc.components.survey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.univcotedazur.mfc.components.registry.SurveyRegistry;
import fr.univcotedazur.mfc.entities.Survey;


@Component
@Transactional
public class SurveyResult {

    private SurveyRegistry surveyRegistry;

    @Autowired
    public SurveyResult(SurveyRegistry surveyRegistry){
        this.surveyRegistry = surveyRegistry;
    }

    public void getSurveyResultPrint(Long surveyID){
        surveyRegistry.findById(surveyID).ifPresent(survey -> survey.getQuestions().forEach(question -> {
            System.out.println(question.getQuestionContent());
            question.getAnswers().forEach(answer -> {
                System.out.println(answer.getAnswer());
            });
        }));
    }

    public Survey getSurveyResult(Long surveyID){
        return surveyRegistry.findById(surveyID).orElse(null);
    }

   public int getNbParticipants(Long surveyID){
        Survey survey= surveyRegistry.findById(surveyID).orElse(null);
        if (survey != null) {
            return survey.getParticipants().size();
        }
        return 0;
    }
}
