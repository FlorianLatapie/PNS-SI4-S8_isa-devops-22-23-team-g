package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.exceptions.AdvantageItemNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.ParkingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {AdvantageController.class,
     CustomerController.class,
     PaymentController.class,
     StatsController.class,
     SurveyController.class,
     AnswerSurveyController.class} )
public class GlobalControllerAdvice {

    @ExceptionHandler({AdvantageItemNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(AdvantageItemNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Advantage not found");
        return errorDTO;
    }

    @ExceptionHandler({ParkingException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(ParkingException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Parking not allowed");
        return errorDTO;
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(CustomerNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer not found");
        return errorDTO;
    }

    @ExceptionHandler({CustomerAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(CustomerAlreadyExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer error");
        return errorDTO;
    }


    @ExceptionHandler({CustomerDoesntHaveAdvantageException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(CustomerDoesntHaveAdvantageException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer does not have advantage");
        return errorDTO;
    }

    @ExceptionHandler({AlreadyAnsweredException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(AlreadyAnsweredException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Client already answered to this survey");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }


}