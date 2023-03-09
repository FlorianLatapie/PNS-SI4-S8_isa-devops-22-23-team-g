package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.interfaces.addAnswer;
import fr.univcotedazur.simpletcfs.repositories.SurveyRepository;

import java.util.UUID;

public class SurveyResponse implements addAnswer {
    SurveyRegistry surveyRegistry;
    public SurveyResponse(SurveyRepository surveyRepository){
        this.surveyRegistry = new SurveyRegistry(surveyRepository);
    }

    public SurveyResponse(SurveyRegistry surveyRegistry){
        this.surveyRegistry = surveyRegistry;
    }

    @Override
    public Survey addAnswer(String answer, UUID question, UUID surveyID, Customer customer) throws AlreadyAnsweredException {
        Survey survey = surveyRegistry.findById(surveyID);
        if (survey == null) {
            throw new NullPointerException("The survey doesn't exist");
        }
        if(!survey.getParticipants().contains(customer)){
            surveyRegistry.addAnswer(answer,question,surveyID);
            surveyRegistry.findById(surveyID).getParticipants().add(customer);
        }
        else {
            throw new AlreadyAnsweredException("The client already contribute to this survey");
        }
        return survey;
    }
}
