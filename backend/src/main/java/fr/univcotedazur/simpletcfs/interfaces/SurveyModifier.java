package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;

public interface SurveyModifier {

    Survey newSurvey(Survey survey);
    Survey newSurvey();
    Survey addQuestion(Long surveyID, Question question);
    Survey addAnswer(String answer, Long question,Long surveyID);
    Survey removeQuestion(Long question,Long surveyID);

}
