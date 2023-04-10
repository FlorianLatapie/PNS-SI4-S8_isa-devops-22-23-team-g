package fr.univcotedazur.mfc.interfaces;

import fr.univcotedazur.mfc.entities.Question;
import fr.univcotedazur.mfc.entities.Survey;

public interface SurveyModifier {

    Survey newSurvey(Survey survey);
    Survey newSurvey();
    Survey addQuestion(Long surveyID, Question question);
    Survey addAnswer(String answer, Long question,Long surveyID);
    Survey removeQuestion(Long question,Long surveyID);

}
