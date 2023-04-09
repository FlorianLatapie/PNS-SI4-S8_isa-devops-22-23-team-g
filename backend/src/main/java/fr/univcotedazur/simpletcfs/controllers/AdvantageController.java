package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import fr.univcotedazur.simpletcfs.controllers.dto.payement.*;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageFinder;
import fr.univcotedazur.simpletcfs.interfaces.AdvantagePayement;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.PointPayement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static fr.univcotedazur.simpletcfs.controllers.CustomerController.convertCustomerToDto;

@RestController
@RequestMapping(path = AdvantageController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AdvantageController {

    public static final String BASE_URI = "/advantage";

    @Autowired
    private AdvantagePayement advantagePayement;

    @Autowired
    private PointPayement pointPayement;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private AdvantageFinder advantageFinder;
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)


    @GetMapping(path = "catalog", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ListAdvantageItemDTO> getCatalog(){
        List<AdvantageItem> advantagesCatalog =  advantageFinder.findAllAdvantages();
        List<AdvantageItemDTO> advantageItemDTOList = new ArrayList<>();
        for (   AdvantageItem advantageItem : advantagesCatalog) {
            advantageItemDTOList.add(new AdvantageItemDTO(advantageItem));
        }
        ListAdvantageItemDTO listAdvantageItemDTO = new ListAdvantageItemDTO(advantageItemDTOList);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listAdvantageItemDTO);
    }

    @GetMapping(path = "/{customerId}/getCustomerAdvantages", produces = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ListAdvantageItemDTO> getCustomerAdvantages(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
            Customer customer = customerFinder.findCustomer(customerId);
            
            List<AdvantageItem> advantagesCatalog = customer.getAdvantageItems();
            List<AdvantageItemDTO> advantageItemDTOList = new ArrayList<>();
            for (AdvantageItem advantageItem : advantagesCatalog) {
                advantageItemDTOList.add(new AdvantageItemDTO(advantageItem));
            }

            ListAdvantageItemDTO listAdvantageItemDTO = new ListAdvantageItemDTO(advantageItemDTOList);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(listAdvantageItemDTO);
    }


    @PostMapping(path = "/{customerId}/debitAdvantage/{advantageItemId}", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AdvantageTransactionDTO> debitAdvantage(@PathVariable("customerId") Long customerId,@PathVariable("advantageItemId") long advantageItemId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException, CustomerDoesntHaveAdvantageException, ParkingException {
        Customer customer = customerFinder.findCustomer(customerId);
        AdvantageTransaction advantageTransaction = advantagePayement.debitAdvantage(customer,retrieveAdvantageItem(advantageItemId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertAdvantageTransactionToDto(advantageTransaction));
    }

    @PostMapping(path = "/{customerId}/payPoints/{advantageItemId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PointTransactionDTO> payPoints(@PathVariable("customerId") Long customerId, @PathVariable("advantageItemId") long advantageItemId, @RequestBody @Valid PaymentDTO paymentDTO) {
        try {
            Customer customer = customerFinder.findCustomer(customerId);
            PointTransaction pointTransaction = pointPayement.payPoints(customer, retrieveAdvantageItem(advantageItemId));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertPointTransactionToDto(pointTransaction));
        } catch (NegativePointBalanceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch(CustomerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Advantage Point information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    private AdvantageItem retrieveAdvantageItem(long advantageItemId) {
        return advantageFinder.findAnAdvantage(advantageItemId);
    }

    static PointTransactionDTO convertPointTransactionToDto(PointTransaction pointTransaction) {
        CustomerDTO customerDTO = convertCustomerToDto(pointTransaction.getCustomer());
        int price = pointTransaction.getPrice();
        Long id = pointTransaction.getId();
        return new PointTransactionDTO(customerDTO,pointTransaction.getAdvantageItem().getTitle(), price, id);
    }

    static AdvantageTransactionDTO convertAdvantageTransactionToDto(AdvantageTransaction advantageTransaction) {
        CustomerDTO customerDTO = convertCustomerToDto(advantageTransaction.getCustomer());
        return new AdvantageTransactionDTO(customerDTO, new AdvantageItemDTO(advantageTransaction.getAdvantage()));
    }



}
