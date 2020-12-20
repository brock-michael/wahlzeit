package org.wahlzeit.model.location;

import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {
    private Coordinate coordinate;

    private Location(final AbstractCoordinate coordinate) {
        AssertionUtils.assertValidCoordinate(coordinate);
        this.coordinate = coordinate;
    }

    public static Location buildWithCartesianCoord(final double x, final double y, final double z) {
        try {
            return new Location(new CartesianCoordinate(x, y, z));
        } catch (IllegalArgumentException | IllegalStateException e) {
            SysLog.log(new StringBuffer("Location with cartesian coordinates: " + x + ", " + y + ", " + z +
                    " not valid. Fall back to default location"));
            return new Location(new CartesianCoordinate(0, 0, 0));
        }
    }

    public static Location buildWithSphericCoord(final double phi, final double theta, final double radius) {
        try {
            return new Location(new SphericCoordinate(phi, theta, radius));
        } catch (IllegalArgumentException | IllegalStateException e) {
            SysLog.log(new StringBuffer("Location with spheric coordinates: " + phi + ", " + theta + ", " + radius +
                    " not valid. Fall back to default location"));
            return new Location(new SphericCoordinate(0, 0, 0));
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCartesianCoord(final double x, final double y, final double z) {
        final CartesianCoordinate coordToSet = new CartesianCoordinate(x, y, z);
        AssertionUtils.assertValidCoordinate(coordToSet);
        this.coordinate = coordToSet;
    }

    public void setSphericCoord(final double phi, final double theta, final double radius) {
        final SphericCoordinate coordToSet = new SphericCoordinate(phi, theta, radius);
        AssertionUtils.assertValidCoordinate(coordToSet);
        this.coordinate = coordToSet;
    }

    public void readFrom(ResultSet rset) throws SQLException {
        this.coordinate.readFrom(rset);
    }

    public void writeOn(final ResultSet rset) throws SQLException {
        this.coordinate.writeOn(rset);
    }
}
