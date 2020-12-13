package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    private double phi;
    private double theta;
    private double radius;

    public SphericCoordinate(final double phi, final double theta, final double radius) {
        AssertionUtils.assertMultibleDouble(phi, theta, radius);
        AssertionUtils.assertNotNegative(radius);
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
        this.assertClassInvariants();
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
        return new CartesianCoordinate(x, y, z);
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
    protected void doReadFrom(final ResultSet rset) throws SQLException {
        this.phi = AssertionUtils.assertAndGetDouble(rset.getDouble("location_phi"));
        this.theta = AssertionUtils.assertAndGetDouble(rset.getDouble("location_theta"));
        this.radius = AssertionUtils.assertAndGetDouble(rset.getDouble("location_radius"));
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

    public void setPhi(final double phi) {
        this.phi = AssertionUtils.assertAndGetDouble(phi);
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(final double theta) {
        this.theta = AssertionUtils.assertAndGetDouble(theta);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(final double radius) {
        this.radius = AssertionUtils.assertAndGetDouble(radius);
    }
}
