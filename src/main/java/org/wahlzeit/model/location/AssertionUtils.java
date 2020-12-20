package org.wahlzeit.model.location;

public class AssertionUtils {
    public static void assertDouble(final double value) {
        if (Double.isNaN(value)) {
            throw new IllegalStateException("Double value has to be a number");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalStateException("Double value can not be infinite");
        }
    }

    public static double assertAndGetDouble(final double value) {
        AssertionUtils.assertDouble(value);
        return value;
    }

    public static void assertMultibleDouble(final Double... values) {
        for (double value : values) {
            AssertionUtils.assertDouble(value);
        }
    }

    public static void assertNotNegative(final double value) {
        if (value < 0) {
            throw new IllegalStateException("Value can not be negative");
        }
    }

    public static void assertNotNegative(final int value) {
        if (value < 0) {
            throw new IllegalStateException("Value can not be negative");
        }
    }

    public static void assertNotNull(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Parameter can not be null");
        }
    }

    public static void assertCentralAngle(final double angle) {
        AssertionUtils.assertDouble(angle);
        if (angle < Math.toRadians(0) || angle > Math.toRadians(180)) {
            throw new IllegalArgumentException("Angle can only be between 0 and 180 degrees.");
        }
    }

    public static void assertValidCoordinate(AbstractCoordinate coordinate) {
        AssertionUtils.assertNotNull(coordinate);
        coordinate.assertClassInvariants();
    }

}
