package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;

import java.util.UUID;

public interface addAnswer {
    Survey addAnswer(String answer, UUID question, UUID surveyID, Customer customer) throws AlreadyAnsweredException;
}
