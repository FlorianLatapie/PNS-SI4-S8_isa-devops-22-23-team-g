package fr.univcotedazur.mfc.components.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.univcotedazur.mfc.components.registry.SurveyRegistry;
import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Survey;
import fr.univcotedazur.mfc.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.mfc.interfaces.SurveyAddAnswer;

import java.util.Optional;

@Component
@Transactional
public class SurveyResponse implements SurveyAddAnswer {
    SurveyRegistry surveyRegistry;

    @Autowired
    public SurveyResponse(SurveyRegistry surveyRegistry){
        this.surveyRegistry = surveyRegistry;
    }

    @Override
    public Survey addAnswer(String answer, Long question, Long surveyID, Customer customer) throws AlreadyAnsweredException {
        Optional<Survey> surveyOpt = surveyRegistry.findById(surveyID);
        if (surveyOpt.isEmpty()) {
            throw new NullPointerException("The survey doesn't exist");
        }

        Survey survey = surveyOpt.get();
        if(!survey.getParticipants().contains(customer)){
            surveyRegistry.addAnswer(answer,question,surveyID);
            surveyRegistry.findById(surveyID).ifPresent(foundSurvey -> foundSurvey.getParticipants().add(customer));
        }
        else {
            throw new AlreadyAnsweredException("The client already contribute to this survey");
        }
        return survey;
    }
}
