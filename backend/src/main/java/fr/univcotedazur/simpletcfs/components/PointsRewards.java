package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Point;
import fr.univcotedazur.simpletcfs.interfaces.PointAdder;
import org.springframework.stereotype.Component;

@Component
public class PointsRewards implements PointAdder {

    @Override
    public Point gain(Customer customer, Point point) {
        customer.addPoint(point);
        return point;
    }
}
