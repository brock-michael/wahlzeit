package org.wahlzeit.model.location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate {
    private double x;
    private double y;
    private double z;

    public CartesianCoordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        final boolean origin = this.x == 0 && this.y == 0 && this.z == 0;
        if (origin) {
            return new SphericCoordinate(0, 0, 0);
        }

        final double radius = Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
        final double phi = Math.atan2(this.y, this.x);
        final double theta = Math.acos(this.z / radius);

        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCartesianDistance(final Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Cant be null");
        }

        final CartesianCoordinate otherAsCart = other.asCartesianCoordinate();
        final double distX = this.getX() - otherAsCart.getX();
        final double distY = this.getY() - otherAsCart.getY();
        final double distZ = this.getZ() - otherAsCart.getZ();

        return Math.sqrt((distX*distX) + (distY*distY) + (distZ*distZ));
    }

    @Override
    public boolean isEqual(final Coordinate other) {
        if (other == null) {
            return false;
        }

        final CartesianCoordinate cart = other.asCartesianCoordinate();
        return  this.compareDouble(cart.x, this.x) &&
                this.compareDouble(cart.y, this.y) &&
                this.compareDouble(cart.z, this.z);
    }

    @Override
    public void readFrom(final ResultSet rset) throws SQLException {
        this.x = rset.getDouble("location_x");
        this.y = rset.getDouble("location_y");
        this.z = rset.getDouble("location_z");
    }

    @Override
    public void writeOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("location_x", this.x);
        rset.updateDouble("location_y", this.y);
        rset.updateDouble("location_z", this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartesianCoordinate that = (CartesianCoordinate) o;
        return this.isEqual(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(final double z) {
        this.z = z;
    }
}
