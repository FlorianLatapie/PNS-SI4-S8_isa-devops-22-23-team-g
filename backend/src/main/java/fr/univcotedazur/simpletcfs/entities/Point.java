package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Point{
    
    int pointAmount;

    public Point(int pointAmount) {
        this.pointAmount = pointAmount;
    }
    
    public Point(){}
    
    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Point subtract(Point amount) {
        return new Point(pointAmount - amount.getPointAmount());
    }

    public Point add(Point amount) {
        return new Point(pointAmount + amount.getPointAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return pointAmount == point.pointAmount;
    }
    
    @Override
    public int hashCode() {
        return ("Point" + pointAmount).hashCode();
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointAmount=" + pointAmount +
                '}';
    }
}
