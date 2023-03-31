package fr.univcotedazur.simpletcfs.components.payment;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.Point;
import fr.univcotedazur.simpletcfs.interfaces.PointAdder;
import org.springframework.stereotype.Component;

@Component
public class PointsRewards implements PointAdder {

    // TODO : JUnit test (spec test)
    // add points to customer : 2 point for each 1 euros spent
    @Override
    public Point gain(Customer customer, Euro amount) {
        Point point = new Point((amount.getCentsAmount() / 100 ) * 2);
        customer.addPoint(point);
        return point;
    }
}
