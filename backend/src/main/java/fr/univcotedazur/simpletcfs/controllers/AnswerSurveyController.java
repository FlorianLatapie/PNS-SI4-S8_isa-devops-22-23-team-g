package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.SurveyDTO;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.interfaces.SurveyAddAnswer;
import fr.univcotedazur.simpletcfs.interfaces.SurveyModifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.univcotedazur.simpletcfs.controllers.CustomerController.convertCustomerToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = SurveyController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AnswerSurveyController {
    public static final String BASE_URI = "/surveyanswers";

    private SurveyAddAnswer surveyAddAnswer;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot add this client answer to the Survey");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({AlreadyAnsweredException.class})
    public ErrorDTO handleExceptions(AlreadyAnsweredException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Client already answered to this survey");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "post", consumes = APPLICATION_JSON_VALUE,params = "surveyID,answer,questionID")
    public ResponseEntity<SurveyDTO> addSurvey(@RequestBody Long surveyID, @RequestBody String answer, @RequestBody Long questionID, @RequestBody Customer customer) throws AlreadyAnsweredException {
        Survey survey;
        try {
            survey = surveyAddAnswer.addAnswer(answer,surveyID,questionID, customer);
        } catch (AlreadyAnsweredException e) {
            handleExceptions(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        System.out.println("Adding new answer for client : " + customer.getUsername() + " to survey " + surveyID);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertSurveyToDto(survey));
    }
    static SurveyDTO convertSurveyToDto(Survey survey) { // In more complex cases, we could use ModelMapper
        System.out.println("Converting survey " + survey.getId() + " to DTO");
        List<CustomerDTO> participants = new ArrayList<>();
        for (int i = 0; i < survey.getParticipants().size(); i++) {
            participants.add(convertCustomerToDto(survey.getParticipants().get(i)));
        }
        return new SurveyDTO(survey.getId(),participants,survey.getQuestions());
    }

}
