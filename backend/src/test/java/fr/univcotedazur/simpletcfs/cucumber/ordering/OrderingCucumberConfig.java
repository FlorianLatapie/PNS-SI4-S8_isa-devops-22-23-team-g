package fr.univcotedazur.simpletcfs.cucumber.ordering;

import fr.univcotedazur.simpletcfs.interfaces.Bank;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest
public class OrderingCucumberConfig {

    @MockBean // Spring/Cucumber bug workaround: declare the mock here, and autowire+setup it in the step classes
    private Bank bankMock;

}
