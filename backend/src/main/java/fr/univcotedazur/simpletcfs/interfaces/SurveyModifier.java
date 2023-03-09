package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdvantageItem;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;

import java.util.List;
import java.util.UUID;

public interface SurveyModifier {

    Survey newSurvey(Survey survey);
    Survey newSurvey();
    Survey addQuestion(UUID surveyID, Question question);
    Survey addAnswer(String answer, UUID question,UUID surveyID);
    Survey removeQuestion(UUID question,UUID surveyID);

}
