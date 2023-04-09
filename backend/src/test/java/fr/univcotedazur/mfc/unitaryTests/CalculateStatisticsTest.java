package fr.univcotedazur.mfc.unitaryTests;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Euro;
import fr.univcotedazur.mfc.entities.EuroTransaction;
import fr.univcotedazur.mfc.entities.statistics.Statistic;
import fr.univcotedazur.mfc.entities.statistics.StatisticValueEuro;
import fr.univcotedazur.mfc.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.mfc.interfaces.CustomerModifier;
import fr.univcotedazur.mfc.interfaces.EuroTransactionModifier;
import fr.univcotedazur.mfc.interfaces.StatsFinder;
import fr.univcotedazur.mfc.repositories.CustomerRepository;
import fr.univcotedazur.mfc.repositories.EuroTransactionRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CalculateStatisticsTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EuroTransactionRepository euroTransactionRepository;

    @Autowired
    private EuroTransactionModifier euroTransactionModifier;

    @Autowired
    private CustomerModifier customerModifier;

    @Autowired
    private StatsFinder statsFinder;

    private Customer customer;

    @BeforeEach
    void setUp() throws CustomerAlreadyExistsException {
        customer = customerModifier.signup("jean-claude", null);
    }

    private void addTransaction(int amount) {
        euroTransactionModifier.add(new EuroTransaction(customer, new Euro(amount), new Date()));
    }

    @Test
    void calculateStatisticsEmpty() {
        Statistic res = statsFinder.computeStatsAllShop();
        assertEquals(new Euro(0), ((StatisticValueEuro) res.getStatisticValues().get(0)).getValueEuro());
    }

    @Test
    void calculateStatistics() {
        addTransaction(1000);
        addTransaction(2000);
        addTransaction(3000);
        Statistic res = statsFinder.computeStatsAllShop();
        assertEquals(new Euro(6000), ((StatisticValueEuro) res.getStatisticValues().get(0)).getValueEuro());
    }

    @Test
    void calculateStatisticsNonIntValue() {
        addTransaction(101);
        Statistic res = statsFinder.computeStatsAllShop();
        assertEquals(new Euro(100), ((StatisticValueEuro) res.getStatisticValues().get(0)).getValueEuro());
    }

    @Test
    void calculateStatisticsNonMultipleIntValue() {
        addTransaction(101);
        addTransaction(202);
        Statistic res = statsFinder.computeStatsAllShop();
        assertEquals(new Euro(300), ((StatisticValueEuro) res.getStatisticValues().get(0)).getValueEuro());
    }

    @Test
    void calculateStatisticsNonMultipleIntValueWithRound() {
        addTransaction(151);
        addTransaction(151);
        Statistic res = statsFinder.computeStatsAllShop();
        assertEquals(new Euro(300), ((StatisticValueEuro) res.getStatisticValues().get(0)).getValueEuro());
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
        euroTransactionRepository.deleteAll();

    }
}
