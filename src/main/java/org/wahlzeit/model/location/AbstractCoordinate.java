package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate implements Coordinate {

    protected abstract void assertClassInvariants();

    protected abstract boolean doCheckForEquality(final Coordinate coordinate);

    protected abstract void doReadFrom(final ResultSet rset) throws SQLException;

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
    public void readFrom(final ResultSet rset) throws SQLException {
        this.assertClassInvariants();
        AssertionUtils.assertNotNull(rset);

        this.doReadFrom(rset);

        this.assertClassInvariants();
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
