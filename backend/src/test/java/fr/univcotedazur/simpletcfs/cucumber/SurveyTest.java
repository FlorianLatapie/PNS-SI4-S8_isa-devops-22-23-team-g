package fr.univcotedazur.simpletcfs.cucumber;


import fr.univcotedazur.simpletcfs.components.registry.QuestionRegistry;
import fr.univcotedazur.simpletcfs.components.survey.SurveyResponse;
import fr.univcotedazur.simpletcfs.components.survey.SurveyResult;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import fr.univcotedazur.simpletcfs.interfaces.SurveyFinder;
import fr.univcotedazur.simpletcfs.interfaces.SurveyModifier;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SurveyTest {
    Long customerId;

    @Autowired
    SurveyFinder surveyFinder;

    @Autowired
    SurveyModifier surveyModifier;

    @Autowired
    QuestionRegistry questionRegistry;

    @Autowired
    SurveyResponse surveyResponse;
    Long surveyId;

    @Autowired
    SurveyResult surveyResult;

    @Autowired
    CustomerFinder customerFinder;

    @Autowired
    CustomerModifier customerModifier;

    @Etantdonné("Un client et un sondage avec une question")
    public void unClientEtUnSondageAvecUneQuestion() throws CustomerAlreadyExistsException {
        Customer customer = customerModifier.signup("Billy", null);
        customerId = customer.getId();
        Survey survey = surveyModifier.newSurvey(new Survey());
        surveyId = survey.getId();
        Question question = questionRegistry.save(new Question("What is your favorite loyalty card?"));
        surveyModifier.addQuestion(survey.getId(), question);
    }


    @Quand("le client répond au sondage")
    public void leClientRépondAuSondage() throws CustomerNotFoundException {
        Customer customer = customerFinder.findCustomer(customerId);
        Survey survey = surveyFinder.findById(surveyId).get();
        assertDoesNotThrow(()->surveyResponse.addAnswer("Loyalty card",survey.getQuestions().get(0).getId(),survey.getId(),customer));
    }

    @Alors("le sondage contient un participant et une réponse de plus")
    public void leSondageContientUnParticipantEtUneRéponseDePlus() {
        Survey finalSurvey = surveyFinder.findById(surveyId).get();
        assertEquals(1,finalSurvey.getParticipants().size());
        assertEquals(1,finalSurvey.getQuestions().get(0).getAnswers().size());// errors
    }

    @Alors("si le client répond encore au sondage, une erreur apparait")
    public void siLeClientRépondEncoreAuSondageUneErreurApparait() throws CustomerNotFoundException {
        Survey survey = surveyFinder.findById(surveyId).get();
        Customer customer = customerFinder.findCustomer(customerId);
        assertThrows(AlreadyAnsweredException.class,()->surveyResponse.addAnswer("Loyalty card",survey.getQuestions().get(0).getId(),survey.getId(),customer));
    }

    /*
    @Etantdonné("Un administrateur")
    public void unAdministrateur() {}

    @Quand("l'administrateur crée un sondage")
    public void lAdministrateurCréeUnSondage() {
        survey = new Survey();
        surveyRegistry.newSurvey(survey);
    }

    @Alors("il existe un sondage sans question")
    public void ilExisteUnSondageSansQuestion() {
        Survey finalSurvey = surveyRegistry.findById(survey.getId()).get();
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
        assertNotNull(survey = surveyRegistry.findById(survey.getId()).get());
    }

    @Alors("il y a {int} réponses à la question et {int} participants")
    public void ilYARéponsesÀLaQuestionEtParticipants(int nbReponses, int nbParticipants) {
        assertEquals(nbReponses,surveyResult.getSurveyResult(survey.getId()).getQuestions().get(0).getAnswers().size());
        assertEquals(nbParticipants,survey.getParticipants().size());
    }

     */
}
