package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {
    private Coordinate coordinate;

    private Location(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public static Location buildWithCartesianCoord(final double x, final double y, final double z) {
        return new Location(new CartesianCoordinate(x, y, z));
    }

    public static Location buildWithSphericCoord(final double phi, final double theta, final double radius) {
        return new Location(new SphericCoordinate(phi, theta, radius));
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCartesianCoord(final double x, final double y, final double z) {
        this.coordinate = new CartesianCoordinate(x, y, z);
    }

    public void setSphericCoord(final double phi, final double theta, final double radius) {
        this.coordinate = new SphericCoordinate(phi, theta, radius);
    }

    public void readFrom(ResultSet rset) throws SQLException {
        this.coordinate.readFrom(rset);
    }

    public void writeOn(final ResultSet rset) throws SQLException {
        this.coordinate.writeOn(rset);
    }
}
