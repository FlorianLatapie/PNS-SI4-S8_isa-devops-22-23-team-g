package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;

public interface SurveyAddAnswer {
    Survey addAnswer(String answer, Long question, Long surveyID, Customer customer) throws AlreadyAnsweredException;
}
