package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CustomerController {

    public static final String BASE_URI = "/customers";

    @Autowired
    private CustomerFinder finder;

    @Autowired
    private CustomerModifier modifier;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Customer information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<CustomerDTO> register(@RequestBody @Valid CustomerDTO cusDto) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Registering customer " + cusDto.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertCustomerToDto(modifier.signup(cusDto.getName(), null)));
        } catch (CustomerAlreadyExistsException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "login", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<CustomerDTO> login(@RequestBody @Valid CustomerDTO cusDto) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Logging in customer " + cusDto.getName());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertCustomerToDto(finder.login(cusDto.getName())));
        } catch (CustomerNotFoundException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    static CustomerDTO convertCustomerToDto(Customer customer) { // In more complex cases, we could use ModelMapper
        System.out.println("Converting customer " + customer.getUsername() + " to DTO");
        return new CustomerDTO(customer);
    }


}