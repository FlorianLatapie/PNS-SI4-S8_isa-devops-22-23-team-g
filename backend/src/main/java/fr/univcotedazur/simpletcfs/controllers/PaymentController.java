package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.payement.EuroTransactionDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.payement.PaymentDTO;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.ChargeCard;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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

    static EuroTransactionDTO convertEuroTransactionToDto(EuroTransaction euroTransaction) {
        System.out.println("Converting transaction of Customer :" + euroTransaction.getCustomer() + " to DTO");

        CustomerDTO customerDTO = convertCustomerToDto(euroTransaction.getCustomer());
        double price = euroTransaction.getPrice().getEuro();

        String shopName;

        if (euroTransaction.getShop() == null) {
            shopName = "Unknown";
        } else {
            shopName = euroTransaction.getShop().getName();
        }

        UUID id = euroTransaction.getId();
        int pointEarned = euroTransaction.getPointEarned().getPointAmount();

        return new EuroTransactionDTO(customerDTO, price, shopName, id, pointEarned);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Advantage Point information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "/{customerId}/payWithCreditCard", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<EuroTransactionDTO> payWithCreditCard(@PathVariable("customerId") UUID customerId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Customer id " + customerId);
            System.out.println("PaymentDTO " + paymentDTO);

            EuroTransaction euroTransaction = payment.payWithCreditCard(new Euro(paymentDTO.price()), retrieveCustomer(customerId), null, paymentDTO.creditCard());

            System.out.println("Payment done");

            return ResponseEntity.status(HttpStatus.CREATED).body(convertEuroTransactionToDto(euroTransaction));
        } catch (PaymentException e) {
            System.out.println("Payment failed");
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        } catch (NegativePaymentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping(path = "/{customerId}/loadCard", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<EuroTransactionDTO> loadCard(@PathVariable("customerId") UUID customerId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Customer id " + customerId);
            System.out.println("PaymentDTO " + paymentDTO);

            EuroTransaction euroTransaction = chargeCard.chargeCard(new Euro(paymentDTO.price()), retrieveCustomer(customerId), paymentDTO.creditCard());
            System.out.println("Payment done");

            return ResponseEntity.status(HttpStatus.CREATED).body(convertEuroTransactionToDto(euroTransaction));
        } catch (PaymentException e) {
            System.out.println("Payment failed");
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        } catch (NegativePaymentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping(path = "/{customerId}/payWithLoyaltyCard", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<EuroTransactionDTO> payWithLoyaltyCard(@PathVariable("customerId") UUID customerId, @RequestBody @Valid PaymentDTO paymentDTO) throws CustomerNotFoundException {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            System.out.println("Customer id " + customerId);
            System.out.println("PaymentDTO " + paymentDTO);

            EuroTransaction euroTransaction = payment.payWithLoyaltyCard(new Euro(paymentDTO.price()), retrieveCustomer(customerId), null);

            System.out.println("Payment done");

            return ResponseEntity.status(HttpStatus.CREATED).body(convertEuroTransactionToDto(euroTransaction));
        } catch (NegativeEuroBalanceException e) {
            // Should never happen
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        } catch (NegativePaymentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private Customer retrieveCustomer(UUID customerId) throws CustomerNotFoundException {
        System.out.println("Customer Id in retrieve Customer " + customerId);
        // TODO : need to debug
        Customer customer = customerFinder.findCustomer(customerId);
        System.out.println("after retrieve customer" + customer);
        return customer;
    }

    /*
    //add a new payment to registry
    @PostMapping(path = "add", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PaymentDTO> add(@RequestBody @Valid PaymentDTO paymentDTO) {
        System.out.println("Adding payment with value of " + paymentDTO.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertEuroTransactionToDto(euroRegistry.add(new EuroTransaction(paymentDTO.getCustomer(),paymentDTO.getShop(),paymentDTO.getPrice()))));
    }
    */
}
