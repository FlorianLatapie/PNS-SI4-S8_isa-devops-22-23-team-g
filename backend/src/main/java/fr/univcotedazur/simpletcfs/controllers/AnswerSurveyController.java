package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.SurveyDTO;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.interfaces.SurveyAddAnswer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static fr.univcotedazur.simpletcfs.controllers.CustomerController.convertCustomerToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = SurveyController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AnswerSurveyController {
    public static final String BASE_URI = "/surveyanswers";

    private SurveyAddAnswer surveyAddAnswer;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot add this client answer to the Survey");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }


    @PostMapping(path = "post", consumes = APPLICATION_JSON_VALUE,params = "surveyID,answer,questionID")
    public ResponseEntity<SurveyDTO> addSurvey(@RequestBody Long surveyID, @RequestBody String answer, @RequestBody Long questionID, @RequestBody CustomerDTO customerDto) throws AlreadyAnsweredException {
        Survey survey;
        Customer customer = new Customer(customerDto);
        try {
            survey = surveyAddAnswer.addAnswer(answer,surveyID,questionID, customer);
        } catch (AlreadyAnsweredException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertSurveyToDto(survey));
    }
    static SurveyDTO convertSurveyToDto(Survey survey) { // In more complex cases, we could use ModelMapper
        List<CustomerDTO> participants = new ArrayList<>();
        for (int i = 0; i < survey.getParticipants().size(); i++) {
            participants.add(convertCustomerToDto(survey.getParticipants().get(i)));
        }
        return new SurveyDTO(survey.getId(),participants,survey.getQuestions());
    }

}
