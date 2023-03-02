package fr.univcotedazur.simpletcfs.entities;

public record Point(int pointAmount) {


    public Point subtract(Point amount) {
        return new Point(pointAmount - amount.pointAmount());
    }

    public Point add(Point amount) {
        return new Point(pointAmount + amount.pointAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return pointAmount == point.pointAmount;
    }
    public int getPointAmount() {
        return pointAmount;
    }

}
