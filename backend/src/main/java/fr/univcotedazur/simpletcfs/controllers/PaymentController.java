package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.payement.EuroTransactionDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.payement.PaymentDTO;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.ChargeCard;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static fr.univcotedazur.simpletcfs.controllers.CustomerController.convertCustomerToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = PaymentController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class PaymentController {

    public static final String BASE_URI = "/payment";
    @Autowired
    private Payment payment;

    @Autowired
    private ChargeCard chargeCard;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private TimeProvider currentTime;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Advantage Point information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "/{customerId}/payWithCreditCard", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<EuroTransactionDTO> payWithCreditCard(@PathVariable("customerId") Long customerId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException {
        try {
            EuroTransaction euroTransaction = payment.payWithCreditCard(new Euro(paymentDTO.price()), retrieveCustomer(customerId), null, paymentDTO.creditCard(), currentTime.now());

            return ResponseEntity.status(HttpStatus.CREATED).body(convertEuroTransactionToDto(euroTransaction));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        } catch (NegativePaymentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping(path = "/{customerId}/loadCard", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<EuroTransactionDTO> loadCard(@PathVariable("customerId") Long customerId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException {
        try {
            EuroTransaction euroTransaction = chargeCard.chargeCard(new Euro(paymentDTO.price()), retrieveCustomer(customerId), paymentDTO.creditCard(), currentTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertEuroTransactionToDto(euroTransaction));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        } catch (NegativePaymentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping(path = "/{customerId}/payWithLoyaltyCard", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<EuroTransactionDTO> payWithLoyaltyCard(@PathVariable("customerId") Long customerId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException {
        try {
            EuroTransaction euroTransaction = payment.payWithLoyaltyCard(new Euro(paymentDTO.price()), retrieveCustomer(customerId), null, currentTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertEuroTransactionToDto(euroTransaction));
        } catch (NegativeEuroBalanceException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        } catch (NegativePaymentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    static EuroTransactionDTO convertEuroTransactionToDto(EuroTransaction euroTransaction) {
        CustomerDTO customerDTO = convertCustomerToDto(euroTransaction.getCustomer());
        double price = euroTransaction.getPrice().euroAmount();
        String shopName;
        if (euroTransaction.getShop() == null) {
            shopName = "Unknown";
        } else {
            shopName = euroTransaction.getShop().getName();
        }
        Long id = euroTransaction.getId();
        int pointEarned = euroTransaction.getPointEarned().getPointAmount();
        return new EuroTransactionDTO(customerDTO, price, shopName, id, pointEarned);
    }

    private Customer retrieveCustomer(Long customerId) throws CustomerNotFoundException {
        return customerFinder.findCustomer(customerId);
    }
}
