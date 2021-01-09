package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCoordinate implements Coordinate {

    protected static final Map<Integer, AbstractCoordinate> values = new HashMap<>();

    protected abstract void assertClassInvariants();

    protected abstract boolean doCheckForEquality(final Coordinate coordinate);

    protected abstract void doWriteOn(final ResultSet rset) throws SQLException;

    @Override
    public double getCartesianDistance(final Coordinate other) {
       return this.asCartesianCoordinate().getCartesianDistance(other);
    }

    @Override
    public double getCentralAngle(final Coordinate other) {
        return this.asSphericCoordinate().getCentralAngle(other);
    }

    @Override
    public boolean isEqual(final Coordinate other) {
        this.assertClassInvariants();

        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }

        final boolean result = this.doCheckForEquality(other);

        this.assertClassInvariants();
        return result;
    }

    @Override
    public Coordinate readFrom(final ResultSet rset) throws SQLException {
        this.assertClassInvariants();
        AssertionUtils.assertNotNull(rset);

        final AbstractCoordinate coord;
        if (this instanceof CartesianCoordinate) {
            coord = CartesianCoordinate.buildFromResultSet(rset);
        } else if (this instanceof SphericCoordinate) {
            coord = SphericCoordinate.buildFromResultSet(rset);
        } else {
            throw new IllegalStateException("Invalid Coordinate-Type to read");
        }

        coord.assertClassInvariants();

        return coord;
    }

    @Override
    public void writeOn(final ResultSet rset) throws SQLException {
        this.assertClassInvariants();
        AssertionUtils.assertNotNull(rset);

        this.doWriteOn(rset);

        this.assertClassInvariants();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return this.isEqual(that);
    }
}
