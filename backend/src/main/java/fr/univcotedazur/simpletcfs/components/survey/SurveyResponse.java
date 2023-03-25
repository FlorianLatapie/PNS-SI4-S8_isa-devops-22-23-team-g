package fr.univcotedazur.simpletcfs.components.survey;

import fr.univcotedazur.simpletcfs.components.registry.SurveyRegistry;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.interfaces.addAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class SurveyResponse implements addAnswer {
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
            surveyRegistry.findById(surveyID).get().getParticipants().add(customer);
        }
        else {
            throw new AlreadyAnsweredException("The client already contribute to this survey");
        }
        return survey;
    }
}
