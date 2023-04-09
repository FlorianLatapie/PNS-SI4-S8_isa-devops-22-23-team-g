package fr.univcotedazur.simpletcfs.unitaryTests;

import fr.univcotedazur.simpletcfs.components.advantages.StatusUpdater;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.CustomerNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

import static fr.univcotedazur.simpletcfs.entities.Status.CLASSIC;
import static fr.univcotedazur.simpletcfs.entities.Status.VFP;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StatusUpdaterTest {

    @Autowired
    private StatusUpdater statusUpdater;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerModifier customerModifier;

    @Autowired
    private CustomerFinder customerFinder;

    private Customer customer;
    private final String customerName = "michel";
    @BeforeEach
    void setUp() throws CustomerAlreadyExistsException {
        customer = customerModifier.signup(customerName, null);
    }

    @Test
    void updateStatusNoChange() throws CustomerNotFoundException {
        Date newPaymentDate = new Date();
        Date oldPaymentDate = createOffsetedDate(newPaymentDate, -8);
        customer.setLastEuroTransactionDate(oldPaymentDate);
        statusUpdater.updateStatus(customer, new EuroTransaction(customer, new Euro(100), newPaymentDate));
        assertEquals(CLASSIC, customer.getStatus());

        Customer customerInBD = customerFinder.login(customerName);
        assertEquals(CLASSIC, customerInBD.getStatus());
    }

    @Test
    void updateStatus() throws CustomerNotFoundException {
        Date newPaymentDate = new Date();
        Date oldPaymentDate = createOffsetedDate(newPaymentDate, -1);
        customer.setLastEuroTransactionDate(oldPaymentDate);
        statusUpdater.updateStatus(customer, new EuroTransaction(customer, new Euro(100), newPaymentDate));
        assertEquals(VFP, customer.getStatus());

        Customer customerInBD = customerFinder.login(customerName);
        assertEquals(VFP, customerInBD.getStatus());
    }

    @Test
    void updateStatusWithNoChangeLimit() throws CustomerNotFoundException {
        Date newPaymentDate = new Date();
        Date oldPaymentDate = createOffsetedDate(newPaymentDate, -7);
        customer.setLastEuroTransactionDate(oldPaymentDate);
        statusUpdater.updateStatus(customer, new EuroTransaction(customer, new Euro(100), newPaymentDate));
        assertEquals(CLASSIC, customer.getStatus());

        Customer customerInBD = customerFinder.login(customerName);
        assertEquals(CLASSIC, customerInBD.getStatus());
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    private Date createOffsetedDate(Date from, int dayOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        calendar.add(Calendar.DAY_OF_YEAR, dayOffset);
        return calendar.getTime();
    }
}
