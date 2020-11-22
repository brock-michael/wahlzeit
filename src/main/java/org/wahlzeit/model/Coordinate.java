package org.wahlzeit.model;

import java.util.Objects;

public class Coordinate {
    private static final double COMPARE_THRESHOLD = .0001;

    private double x;
    private double y;
    private double z;

    public Coordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistance(final Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Cant be null");
        }
        final double distX = this.x - other.x;
        final double distY = this.y - other.y;
        final double distZ = this.z - other.z;

        return Math.sqrt((distX*distX) + (distY*distY) + (distZ*distZ));
    }

    public boolean isEqual(final Coordinate other) {
        return other != null &&
                this.compareDouble(other.x, this.x) &&
                this.compareDouble(other.y, this.y) &&
                this.compareDouble(other.z, this.z);
    }

    private boolean compareDouble(double d1, double d2) {
        return Math.abs(d1 - d2) < COMPARE_THRESHOLD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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
