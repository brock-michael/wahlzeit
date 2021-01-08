package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SphericCoordinate extends AbstractCoordinate {
    private final double phi;
    private final double theta;
    private final double radius;

    private static final Map<Integer, SphericCoordinate> values = new HashMap<>();

    private SphericCoordinate(final double phi, final double theta, final double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    public static SphericCoordinate build(final double phi, final double theta, final double radius) {
        AssertionUtils.assertMultibleDouble(phi, theta, radius);
        AssertionUtils.assertNotNegative(radius);
        final SphericCoordinate coord = new SphericCoordinate(phi, theta, radius);
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
        final double phi = AssertionUtils.assertAndGetDouble(rset.getDouble("location_phi"));
        final double theta = AssertionUtils.assertAndGetDouble(rset.getDouble("location_theta"));
        final double radius = AssertionUtils.assertAndGetDouble(rset.getDouble("location_radius"));
        return CartesianCoordinate.build(phi, theta, radius);
    }

    @Override
    protected void assertClassInvariants() {
        AssertionUtils.assertDouble(this.phi);
        AssertionUtils.assertDouble(this.theta);
        AssertionUtils.assertDouble(this.radius);
        AssertionUtils.assertNotNegative(this.radius);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        this.assertClassInvariants();

        final CartesianCoordinate cartesianCoordinate = this.doConvertToCartesianCoordinate();

        AssertionUtils.assertValidCoordinate(cartesianCoordinate);
        this.assertClassInvariants();
        return cartesianCoordinate;
    }

    private CartesianCoordinate doConvertToCartesianCoordinate() {
        final double x = this.radius * Math.sin(this.theta) * Math.cos(this.phi);
        final double y = this.radius * Math.sin(this.theta) * Math.sin(this.phi);
        final double z = this.radius * Math.cos(this.theta);
        return CartesianCoordinate.build(x, y, z);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(final Coordinate other) {
        this.assertClassInvariants();
        AssertionUtils.assertNotNull(other);

        final SphericCoordinate otherAsSpheric = other.asSphericCoordinate();
        final double centralAngle = this.doGetCentralAngle(otherAsSpheric);

        AssertionUtils.assertCentralAngle(centralAngle);
        this.assertClassInvariants();
        return centralAngle;
    }

    private double doGetCentralAngle(final SphericCoordinate other) {
        final double sin = Math.sin(this.phi) * Math.sin(this.phi);
        final double deltaTheta = Math.abs(other.theta-this.theta);
        final double cos = Math.cos(this.phi) * Math.cos(other.phi) * Math.cos(deltaTheta);
        return Math.acos(sin + cos);
    }

    @Override
    protected boolean doCheckForEquality(final Coordinate other) {
        final SphericCoordinate cart = other.asSphericCoordinate();
        return  CompareUtils.compareDouble(cart.phi, this.phi) &&
                CompareUtils.compareDouble(cart.theta, this.theta) &&
                CompareUtils.compareDouble(cart.radius, this.radius);
    }

    @Override
    protected void doWriteOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("location_phi", this.phi);
        rset.updateDouble("location_theta", this.theta);
        rset.updateDouble("location_radius", this.radius);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

    public double getPhi() {
        return phi;
    }

    public double getTheta() {
        return theta;
    }

    public double getRadius() {
        return radius;
    }
}
