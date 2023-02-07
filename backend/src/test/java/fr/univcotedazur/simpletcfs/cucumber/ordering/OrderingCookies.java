package fr.univcotedazur.simpletcfs.cucumber.ordering;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.simpletcfs.exceptions.EmptyCartException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeQuantityException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import fr.univcotedazur.simpletcfs.repositories.OrderRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class OrderingCookies {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartModifier cartModifier;

    @Autowired
    private CartProcessor cartProcessor;

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired // Spring/Cucumber bug workaround: autowired the mock declared in the Config class
    private Bank bankMock;

    private Customer customer;
    private Set<Item> cartContents;
    private Order order;

    @Before
    public void settingUpContext() throws PaymentException {
        customerRepository.deleteAll();
        orderRepository.deleteAll();
        when(bankMock.pay(any(Customer.class), anyDouble())).thenReturn(true);
    }

    @Given("a customer named {string} with credit card {string}")
    public void aCustomerNamedWithCreditCard(String customerName, String creditCard) throws AlreadyExistingCustomerException {
        customerRegistration.register(customerName, creditCard);
    }

    @When("{string} asks for his cart contents")
    public void customerAsksForHisCartContents(String customerName) {
        customer = customerFinder.findByName(customerName).get();
        cartContents = cartProcessor.contents(customer);
    }

    @Then("^there (?:is|are) (\\d+) items? inside the cart$") // Regular Expressions, not Cucumber expression
    // Note that you cannot mix Cucumber expression such as {int} with regular expressions
    public void thereAreItemsInsideTheCart(int nbItems) {
        assertEquals(nbItems, cartContents.size());
    }

    @When("{string} orders {int} x {string}")
    public void customerOrders(String customerName, int howMany, String recipe) throws NegativeQuantityException {
        customer = customerFinder.findByName(customerName).get();
        Cookies cookie = Cookies.valueOf(recipe);
        cartModifier.update(customer, new Item(cookie, howMany));
    }

    @And("the cart contains the following item: {int} x {string}")
    public void theCartContainsTheFollowingItem(int howMany, String recipe) {
        Item expected = new Item(Cookies.valueOf(recipe), howMany);
        assertTrue(cartContents.contains(expected));
    }

    @And("{string} decides not to buy {int} x {string}")
    public void customerDecidesNotToBuy(String customerName, int howMany, String recipe) throws NegativeQuantityException {
        customer = customerFinder.findByName(customerName).get();
        Cookies cookie = Cookies.valueOf(recipe);
        cartModifier.update(customer, new Item(cookie, -howMany));
    }

    @Then("the price of {string}'s cart is equals to {double}")
    public void thePriceOfSebSCartIsEqualsTo(String customerName, double expectedPrice) {
        customer = customerFinder.findByName(customerName).get();
        assertEquals(expectedPrice, cartProcessor.price(customer), 0.01);
    }

    @And("{string} validates the cart and pays through the bank")
    public void validatesTheCart(String customerName) throws EmptyCartException, PaymentException {
        customer = customerFinder.findByName(customerName).get();
        order = cartProcessor.validate(customer);
    }

    @Then("the order amount is equals to {double}")
    public void theOrderAmountIsEqualsTo(double expectedPrice) {
        assertEquals(expectedPrice, order.getPrice(), 0.01);
    }

    @Then("the order status is {string}")
    public void theOrderStatusIs(String state) {
        assertEquals(OrderStatus.valueOf(state), order.getStatus());
    }

}
