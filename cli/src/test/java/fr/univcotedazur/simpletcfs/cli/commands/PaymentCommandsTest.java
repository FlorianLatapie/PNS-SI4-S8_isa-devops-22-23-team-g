package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.model.CliCustomer;
import fr.univcotedazur.simpletcfs.cli.model.EuroTransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(PaymentCommands.class)
@ComponentScan(basePackages = "fr.univcotedazur.simpletcfs.cli")
class PaymentCommandsTest {
    @Autowired
    private PaymentCommands client;

    @Autowired
    private CustomerCommands customerCommands;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void payWithCreditCardTest() {
        server
                .expect(requestTo("/customers/login"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"name\":\"Jeanne\", \"id\":\"123\"}", MediaType.APPLICATION_JSON));

        server
                .expect(requestTo("/payment/123/payWithCreditCard"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"customerDTO\":{\"name\":\"Jeanne\",\"id\":\"123\"},\"price\":\"10\",\"shopName\":\"myShopName\",\"idTransaction\":\"123456789\",\"pointsEarned\":\"1\"}",MediaType.APPLICATION_JSON));
        customerCommands.login("Jeanne");

        var expected = new EuroTransactionDTO(new CliCustomer("Jeanne", 123), 10, "myShopName", 123456789, 1);
        assertEquals(expected, client.payWithCreditCard("10", "896983", "Jeanne"));
    }
}
