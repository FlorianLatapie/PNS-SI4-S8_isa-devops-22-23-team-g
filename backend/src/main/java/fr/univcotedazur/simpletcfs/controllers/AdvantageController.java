package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.PointTransactionRegistry;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = AdvantageController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AdvantageController {

    public static final String BASE_URI = "/payment/advantage";
    @Autowired
    private PointTransactionRegistry pointRegistry;
    //TODO: implement the controller
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Advantage Point information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

}
