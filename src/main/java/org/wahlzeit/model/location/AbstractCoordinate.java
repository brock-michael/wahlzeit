package org.wahlzeit.model.location;

public abstract class AbstractCoordinate implements Coordinate {
    private static final double COMPARE_THRESHOLD = .0001;

    protected boolean compareDouble(final double d1, final double d2) {
        return Math.abs(d1 - d2) < COMPARE_THRESHOLD;
    }

    @Override
    public double getCartesianDistance(final Coordinate other) {
        return this.asCartesianCoordinate().getCartesianDistance(other);
    }

    @Override
    public double getCentralAngle(final Coordinate other) {
        return this.asSphericCoordinate().getCentralAngle(other);
    }

}
