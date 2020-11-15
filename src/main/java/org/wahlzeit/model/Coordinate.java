package org.wahlzeit.model;

import java.util.Objects;

public class Coordinate {
    private double x;
    private double y;
    private double z;

    public Coordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistance(final Coordinate other) {
        final double distX = this.x - other.x;
        final double distY = this.y - other.y;
        final double distZ = this.z - other.z;

        return Math.sqrt((distX*distX) + (distY*distY) + (distZ*distZ));
    }

    public boolean isEqual(final Coordinate other) {
        return other != null &&
                Double.compare(other.x, this.x) == 0 &&
                Double.compare(other.y, this.y) == 0 &&
                Double.compare(other.z, this.z) == 0;
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
