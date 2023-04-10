package fr.univcotedazur.mfc.components.payment;

import org.springframework.stereotype.Component;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Euro;
import fr.univcotedazur.mfc.entities.Point;
import fr.univcotedazur.mfc.interfaces.PointAdder;

@Component
public class PointsRewards implements PointAdder {

    @Override
    public Point gain(Customer customer, Euro amount) {
        Point point = new Point((amount.getCentsAmount() / 100 ) * 2);
        customer.addPoint(point);
        return point;
    }
}
