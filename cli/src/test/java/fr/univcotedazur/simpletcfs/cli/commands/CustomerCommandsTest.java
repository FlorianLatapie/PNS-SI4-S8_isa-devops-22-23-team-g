package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.model.CliCustomer;
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

@RestClientTest(CustomerCommands.class)
@ComponentScan(basePackages = "fr.univcotedazur.simpletcfs.cli")
class CustomerCommandsTest {
    @Autowired
    private CustomerCommands client;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void registerTest() {
        server
                .expect(requestTo("/customers/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"name\":\"test\", \"id\":\"123\"}", MediaType.APPLICATION_JSON));

        assertEquals(new CliCustomer("test",123), client.register("test"));
    }

    @Test
    void registerForAlreadyExistingCustomerTest() {
        server
                .expect(requestTo("/customers/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CONFLICT));

        try {
            client.register("test");
        } catch (Exception e) {
            assertEquals("409 Conflict: [no body]", e.getMessage());
        }
    }

    @Test
    void loginTest(){
        server
                .expect(requestTo("/customers/login"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"name\":\"test\", \"id\":\"123\"}", MediaType.APPLICATION_JSON));

        assertEquals(new CliCustomer("test",123), client.login("test"));
    }

    @Test
    void customersTest(){
        // two calls to the server for login

        server
                .expect(requestTo("/customers/login"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"name\":\"test\", \"status\": \"CLASSIC\"}", MediaType.APPLICATION_JSON));

        server
                .expect(requestTo("/customers/login"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"name\":\"test2\", \"status\": \"CLASSIC\"}", MediaType.APPLICATION_JSON));
        client.login("test");
        client.login("test2");

        assertEquals("{test2=CliCustomer{id=null, name='test2', points=0, euros=0.0, status=CLASSIC}, test=CliCustomer{id=null, name='test', points=0, euros=0.0, status=CLASSIC}}", client.customers());
    }
}
