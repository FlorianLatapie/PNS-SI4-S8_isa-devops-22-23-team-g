package fr.univcotedazur.simpletcfs.unitaryTests;

import fr.univcotedazur.simpletcfs.components.payment.PointsRewards;
import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CalculatePointsTest {

    @Autowired
    private PointsRewards pointsRewards;

    @BeforeEach
    void setUp() {

    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "100, 2",
            "251, 4",
            "299, 4",
    })
    void calculatePoints(int input, int expected) {
        Customer client = new Customer("henry");
        // 100 cents = 1 euro --> 2 points
        Point point = pointsRewards.gain(client, new Euro(input));
        assertEquals(calculate(input), point.getPointAmount());
        assertEquals(expected, point.getPointAmount());
    }

    private int calculate(int amount) {
        return amount / 100 * 2;
    }
}
