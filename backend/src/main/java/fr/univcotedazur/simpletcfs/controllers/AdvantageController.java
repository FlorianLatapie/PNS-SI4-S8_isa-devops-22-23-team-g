package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.registry.PointTransactionRegistry;
import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import fr.univcotedazur.simpletcfs.controllers.dto.payement.*;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AdvantageItemNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;
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
import java.util.UUID;

import static fr.univcotedazur.simpletcfs.controllers.CustomerController.convertCustomerToDto;

@RestController
@RequestMapping(path = AdvantageController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AdvantageController {

    public static final String BASE_URI = "/advantage";
    @Autowired
    private PointTransactionRegistry pointRegistry;


    @Autowired
    private AdvantagePayement advantagePayement;

    @Autowired
    private PointPayement pointPayement;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private AdvantageFinder advantageFinder;
    //TODO: implement the controller
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    
//    @GetMapping(path = "/catalog", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
//    public ResponseEntity<AdvantageItemDTO> geteAdvantageItem(){
//        List<AdvantageItem> advantagesCatalog =  advantageItemFinder.findAllAdvantages();
//        List<AdvantageItemDTO> advantageItemDTOList = new ArrayList<>();
//        for (   AdvantageItem advantageItem : advantagesCatalog) {
//            advantageItemDTOList.add(new AdvantageItemDTO(advantageItem));
//
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(advantageItemDTOList.toArray(new AdvantageItemDTO[0]));
//
//    }


    @GetMapping(path = "catalog", produces = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ListAdvantageItemDTO> getCatalog(){
        System.out.println("Response received getCatalog");
        List<AdvantageItem> advantagesCatalog =  advantageFinder.findAllAdvantages();
        List<AdvantageItemDTO> advantageItemDTOList = new ArrayList<>();
        for (   AdvantageItem advantageItem : advantagesCatalog) {
            advantageItemDTOList.add(new AdvantageItemDTO(advantageItem));
        }
//
//        AdvantageItemDTO[] array = new AdvantageItemDTO[advantageItemDTOList.size()];
//        advantageItemDTOList.toArray(array);

//        AdvantageItemDTO[] array = advantageItemDTOList.toArray(new AdvantageItemDTO[0]);

        ListAdvantageItemDTO listAdvantageItemDTO = new ListAdvantageItemDTO(advantageItemDTOList);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listAdvantageItemDTO);

    }

    @GetMapping(path = "/{customerId}/getCustomerAdvantages", produces = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ListAdvantageItemDTO> getCustomerAdvantages(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
            System.out.println("Response received getCustomerAdvantages");
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
    public ResponseEntity<AdvantageTransactionDTO> debitAdvantage(@PathVariable("customerId") Long customerId,@PathVariable("advantageItemId") long advantageItemId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException, AdvantageItemNotFoundException {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Customer id " + customerId);
            System.out.println("PaymentDTO " + paymentDTO);

            Customer customer = customerFinder.findCustomer(customerId);
            AdvantageTransaction advantageTransaction = advantagePayement.debitAdvantage(customer,retrieveAdvantageItem(advantageItemId));
            System.out.println("Payment done");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertAdvantageTransactionToDto(advantageTransaction));
        } catch (CustomerDoesntHaveAdvantageException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping(path = "/{customerId}/payPoints/{advantageItemId}", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    //@PathVariable("advantageItemId") UUID advantageItemId
    public ResponseEntity<PointTransactionDTO> payPoints(@PathVariable("customerId") Long customerId, @PathVariable("advantageItemId") long advantageItemId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException, AdvantageItemNotFoundException {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Customer id " + customerId);
            System.out.println("PaymentDTO " + paymentDTO);
            //TODO quand le catalogue advantage sera fait, il faudra changer le hardcode par l'advantageItemId
//            PointTransaction pointTransaction = pointPayement.payPoints(retrieveCustomer(customerId,customerFinder),new AdvantageItem(Status.CLASSIC,"test","test-d",new Point(100),null));//retrieveAdvantageItem(null));

            Customer customer = customerFinder.findCustomer(customerId);
            PointTransaction pointTransaction = pointPayement.payPoints(customer,retrieveAdvantageItem(advantageItemId));//retrieveAdvantageItem(null));
            System.out.println("Payment done");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertPointTransactionToDto(pointTransaction));

        } catch (NegativePointBalanceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Advantage Point information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }
//    private Customer retrieveCustomer(UUID customerId) throws CustomerNotFoundException {
//        System.out.println("Customer Id in retrieve Customer " + customerId);
//        // TODO : need to debug
//        Customer customer =  customerFinder.findCustomer(customerId);
//        System.out.println("after retrieve customer" + customer);
//        return customer;
//    }

    private AdvantageItem retrieveAdvantageItem(long advantageItemId) throws AdvantageItemNotFoundException {
        System.out.println("Advantage Item Id in retrieve Advantage Item " + advantageItemId);
        // TODO : need to debug
        AdvantageItem advantageItem =  advantageFinder.findAnAdvantage(advantageItemId);
        System.out.println("after retrieve advantageItem" + advantageItem);
        return advantageItem;
    }


    static PointTransactionDTO convertPointTransactionToDto(PointTransaction pointTransaction) {
        System.out.println("Converting transaction of Customer :" + pointTransaction.getCustomer() + " to DTO");
        CustomerDTO customerDTO = convertCustomerToDto(pointTransaction.getCustomer());
        int price = pointTransaction.getPrice();
        Long id = pointTransaction.getId();
        return new PointTransactionDTO(customerDTO,pointTransaction.getAdvantageItem().getTitle(), price, id);
    }

    static AdvantageTransactionDTO convertAdvantageTransactionToDto(AdvantageTransaction advantageTransaction) {
        System.out.println("Converting transaction of Customer :" + advantageTransaction.getCustomer() + " to DTO");
        CustomerDTO customerDTO = convertCustomerToDto(advantageTransaction.getCustomer());
        return new AdvantageTransactionDTO(customerDTO, advantageTransaction.getAdvantageName(), UUID.randomUUID());
    }

}
