package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(final Coordinate other);

    SphericCoordinate asSphericCoordinate();

    double getCentralAngle(final Coordinate other);

    boolean isEqual(final Coordinate other);

    void readFrom(final ResultSet rset) throws SQLException;

    void writeOn(final ResultSet rset) throws SQLException;

}
