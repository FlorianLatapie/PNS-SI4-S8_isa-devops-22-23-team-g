package fr.univcotedazur.mfc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.univcotedazur.mfc.controllers.dto.ErrorDTO;
import fr.univcotedazur.mfc.exceptions.AdvantageItemNotFoundException;
import fr.univcotedazur.mfc.exceptions.AlreadyAnsweredException;
import fr.univcotedazur.mfc.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.mfc.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.mfc.exceptions.CustomerNotFoundException;
import fr.univcotedazur.mfc.exceptions.ParkingException;

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