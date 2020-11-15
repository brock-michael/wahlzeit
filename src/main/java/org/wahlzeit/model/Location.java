package org.wahlzeit.model;

public class Location {
    private final Coordinate coordinate;

    private Location(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public static Location buildWithCartesianCoord(final double x, final double y, final double z) {
        return new Location(new Coordinate(x, y, z));
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCartCoordinate(final double x, final double y, final double z) {
        this.coordinate.setX(x);
        this.coordinate.setY(y);
        this.coordinate.setZ(z);
    }
}
