package fr.univcotedazur.mfc.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univcotedazur.mfc.connectors.BankProxy;
import fr.univcotedazur.mfc.interfaces.Park;


@CucumberContextConfiguration
@SpringBootTest
public class CatalogCucumberRunnerIT { // IT suffix on test classes make them "Integration Test" run by "verify" goal in maven (see pom.xml)

    @MockBean // Spring/Cucumber bug workaround: declare the mock here, and autowire+setup it in the step classes
    private BankProxy bankMock;

    @MockBean
    private Park parkMock;

}
