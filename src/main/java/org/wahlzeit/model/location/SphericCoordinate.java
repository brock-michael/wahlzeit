package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    private double phi;
    private double theta;
    private double radius;

    public SphericCoordinate(final double phi, final double theta, final double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
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
        final SphericCoordinate thisAsSpheric = this.asSphericCoordinate();
        final SphericCoordinate otherAsSpheric = other.asSphericCoordinate();

        final double sin = Math.sin(thisAsSpheric.getPhi()) * Math.sin(otherAsSpheric.getPhi());
        final double deltaTheta = Math.abs(otherAsSpheric.getTheta()-thisAsSpheric.getTheta());
        final double cos = Math.cos(thisAsSpheric.getPhi()) * Math.cos(otherAsSpheric.getPhi()) * Math.cos(deltaTheta);
        return Math.acos(sin + cos);
    }

    @Override
    public boolean isEqual(final Coordinate other) {
        if (other == null) {
            return false;
        }

        final SphericCoordinate cart = other.asSphericCoordinate();
        return  this.compareDouble(cart.phi, this.phi) &&
                this.compareDouble(cart.theta, this.theta) &&
                this.compareDouble(cart.radius, this.radius);
    }

    @Override
    public void readFrom(final ResultSet rset) throws SQLException {
        this.phi = rset.getDouble("location_phi");
        this.theta = rset.getDouble("location_theta");
        this.radius = rset.getDouble("location_radius");
    }

    @Override
    public void writeOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("location_phi", this.phi);
        rset.updateDouble("location_theta", this.theta);
        rset.updateDouble("location_radius", this.radius);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SphericCoordinate that = (SphericCoordinate) o;
        return this.isEqual(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
