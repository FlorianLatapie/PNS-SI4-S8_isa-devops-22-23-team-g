package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.SurveyDTO;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.exceptions.SurveyAlreadyExistsException;
import fr.univcotedazur.simpletcfs.interfaces.SurveyFinder;
import fr.univcotedazur.simpletcfs.interfaces.SurveyModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static fr.univcotedazur.simpletcfs.controllers.CustomerController.convertCustomerToDto;

@RestController
@RequestMapping(path = SurveyController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class SurveyController {
    public static final String BASE_URI = "/surveys";


    private SurveyModifier surveyModifier;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Survey information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "add", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<SurveyDTO> addSurvey(@RequestBody @Valid SurveyDTO surveyDTO) {
        System.out.println("Adding new survey " + surveyDTO/*.getName()*/);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertSurveyToDto(surveyModifier.newSurvey()));
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