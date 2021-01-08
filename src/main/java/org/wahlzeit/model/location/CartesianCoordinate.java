package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CartesianCoordinate extends AbstractCoordinate {
    private final double x;
    private final double y;
    private final double z;

    private static final Map<Integer, CartesianCoordinate> values = new HashMap<>();

    private CartesianCoordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static CartesianCoordinate build(final double x, final double y, final double z) {
        AssertionUtils.assertMultibleDouble(x, y, z);
        final CartesianCoordinate coord = new CartesianCoordinate(x, y, z);
        coord.assertClassInvariants();

        int id = coord.hashCode();
        if (values.containsKey(id)) {
            return values.get(id);
        } else {
            values.put(id, coord);
            return coord;
        }
    }

    public static CartesianCoordinate buildFromResultSet(final ResultSet rset) throws SQLException {
        final double x = AssertionUtils.assertAndGetDouble(rset.getDouble("location_x"));
        final double y = AssertionUtils.assertAndGetDouble(rset.getDouble("location_y"));
        final double z = AssertionUtils.assertAndGetDouble(rset.getDouble("location_z"));
        return CartesianCoordinate.build(x, y, z);
    }

    @Override
    protected void assertClassInvariants() {
        AssertionUtils.assertDouble(this.x);
        AssertionUtils.assertDouble(this.y);
        AssertionUtils.assertDouble(this.z);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        this.assertClassInvariants();

        final SphericCoordinate sphericCoordinate = this.doConvertToSphericCoordinate();

        AssertionUtils.assertValidCoordinate(sphericCoordinate);
        this.assertClassInvariants();
        return sphericCoordinate;
    }

    private SphericCoordinate doConvertToSphericCoordinate() {
        final boolean origin = this.x == 0 && this.y == 0 && this.z == 0;
        if (origin) {
            return SphericCoordinate.build(0, 0, 0);
        }

        final double radius = Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
        final double phi = Math.atan2(this.y, this.x);
        final double theta = Math.acos(this.z / radius);

        return SphericCoordinate.build(phi, theta, radius);
    }

    @Override
    public double getCartesianDistance(final Coordinate other) {
        this.assertClassInvariants();
        AssertionUtils.assertNotNull(other);

        final CartesianCoordinate otherAsCart = other.asCartesianCoordinate();
        final double dist = doGetCartesianDistance(otherAsCart);

        AssertionUtils.assertDouble(dist);
        this.assertClassInvariants();
        return dist;
    }

    private double doGetCartesianDistance(final CartesianCoordinate other) {
        final double distX = this.x - other.x;
        final double distY = this.y - other.y;
        final double distZ = this.z - other.z;

        return Math.sqrt((distX*distX) + (distY*distY) + (distZ*distZ));
    }

    @Override
    protected boolean doCheckForEquality(final Coordinate other) {
        final CartesianCoordinate cart = other.asCartesianCoordinate();
        return  CompareUtils.compareDouble(cart.x, this.x) &&
                CompareUtils.compareDouble(cart.y, this.y) &&
                CompareUtils.compareDouble(cart.z, this.z);
    }

    @Override
    protected void doWriteOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("location_x", this.x);
        rset.updateDouble("location_y", this.y);
        rset.updateDouble("location_z", this.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
