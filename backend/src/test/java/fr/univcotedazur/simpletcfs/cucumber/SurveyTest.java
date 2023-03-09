package fr.univcotedazur.simpletcfs.cucumber;


import fr.univcotedazur.simpletcfs.components.SurveyRegistry;
import fr.univcotedazur.simpletcfs.components.SurveyResponse;
import fr.univcotedazur.simpletcfs.components.SurveyResult;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.repositories.SurveyRepository;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class SurveyTest {
    Customer customer;
    SurveyRepository surveyRepository = new SurveyRepository();
    SurveyRegistry surveyRegistry = new SurveyRegistry(surveyRepository);

    SurveyResponse surveyResponse = new SurveyResponse(surveyRegistry);
    Survey survey;

    SurveyResult surveyResult = new SurveyResult(surveyRepository);
    @Etantdonné("Un client et un sondage avec une question")
    public void unClientEtUnSondageAvecUneQuestion() {
        customer = new Customer("Billy");
        survey = new Survey();
        surveyRegistry.newSurvey(survey);
        survey = surveyRegistry.addQuestion(survey.getId(),new Question("What is your favorite loyalty card?"));
    }


    @Quand("le client répond au sondage")
    public void leClientRépondAuSondage() {
        assertDoesNotThrow(()->surveyResponse.addAnswer("Loyalty card",survey.getQuestions().get(0).getId(),survey.getId(),customer));
    }

    @Alors("le sondage contient un participant et une réponse de plus")
    public void leSondageContientUnParticipantEtUneRéponseDePlus() {
        Survey finalSurvey = surveyRegistry.findById(survey.getId());
        assertEquals(1,finalSurvey.getParticipants().size());
        assertEquals(1,finalSurvey.getQuestions().get(0).getAnswers().size());
    }

    @Alors("si le client répond encore au sondage, une erreur apparait")
    public void siLeClientRépondEncoreAuSondageUneErreurApparait() {
        assertThrows(AlreadyAnsweredException.class,()->surveyResponse.addAnswer("Loyalty card",survey.getQuestions().get(0).getId(),survey.getId(),customer));
    }

    @Etantdonné("Un administrateur")
    public void unAdministrateur() {}

    @Quand("l'administrateur crée un sondage")
    public void lAdministrateurCréeUnSondage() {
        survey = new Survey();
        surveyRegistry.newSurvey(survey);
    }

    @Alors("il existe un sondage sans question")
    public void ilExisteUnSondageSansQuestion() {
        Survey finalSurvey = surveyRegistry.findById(survey.getId());
        AtomicInteger count = new AtomicInteger();
        surveyRegistry.findAll().forEach(survey1 -> {count.getAndIncrement();});
        assertEquals(1,count.get());
        assertEquals(0,finalSurvey.getQuestions().size());
    }

    @Etantdonné("Un administrateur et un sondage avec une question et {int} participants")
    public void unAdministrateurEtUnSondageAvecUneQuestionEtParticipants(int nbParticipants) throws AlreadyAnsweredException {
        survey = new Survey();
        surveyRegistry.newSurvey(survey);
        survey = surveyRegistry.addQuestion(survey.getId(),new Question("What is your favorite loyalty card?"));
        for(int i = 0; i < nbParticipants; i++){
            surveyResponse.addAnswer("Loyalty card",survey.getQuestions().get(0).getId(),survey.getId(),new Customer("Billy"+i));
        }
    }

    @Quand("l'administrateur récupère les résultats du sondage")
    public void lAdministrateurRécupèreLesRésultatsDuSondage() {
        assertNotNull(survey = surveyRegistry.findById(survey.getId()));
    }

    @Alors("il y a {int} réponses à la question et {int} participants")
    public void ilYARéponsesÀLaQuestionEtParticipants(int nbReponses, int nbParticipants) {
        assertEquals(nbReponses,surveyResult.getSurveyResult(survey.getId()).getQuestions().get(0).getAnswers().size());
        assertEquals(nbParticipants,survey.getParticipants().size());
    }
}
