package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Survey;
import fr.univcotedazur.mfc.exceptions.AlreadyAnsweredException;

public interface SurveyAddAnswer {
    Survey addAnswer(String answer, Long question, Long surveyID, Customer customer) throws AlreadyAnsweredException;
}
