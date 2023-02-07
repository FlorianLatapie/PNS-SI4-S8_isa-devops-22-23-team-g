package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.EmptyCartException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeQuantityException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {CustomerCareController.class, CartController.class})
public class GlobalControllerAdvice {

    @ExceptionHandler({CustomerIdNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(CustomerIdNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer not found");
        errorDTO.setDetails(e.getId() + " is not a valid customer Id");
        return errorDTO;
    }

    @ExceptionHandler({EmptyCartException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(EmptyCartException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cart is empty");
        errorDTO.setDetails("from Customer " + e.getName());
        return errorDTO;
    }

    @ExceptionHandler({NegativeQuantityException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(NegativeQuantityException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Attempting to update the cookie quantity to a negative value");
        errorDTO.setDetails("from Customer " + e.getName() + "with cookie " + e.getCookie() +
                " leading to quantity " + e.getPotentialQuantity());
        return errorDTO;
    }

    @ExceptionHandler({PaymentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleExceptions(PaymentException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Payment was rejected");
        errorDTO.setDetails("from Customer " + e.getName() + " for amount " + e.getAmount());
        return errorDTO;
    }
}
