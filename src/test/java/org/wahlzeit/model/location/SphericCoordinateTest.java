package org.wahlzeit.model.location;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SphericCoordinateTest {
    private static final double DOUBLE_COMPARE_DELTA = 0.0001;

    @Test
    public void testInitWithInvalidParam() throws Exception {
        SphericCoordinate coord = null;

        try {
            coord = SphericCoordinate.build(Double.NaN, Double.NaN, Double.NaN);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Double value has to be a number");
            assertNull(coord);
        }
    }

    @Test
    public void testInitWithNegativeRadius() throws Exception {
        SphericCoordinate coord = null;

        try {
            coord = SphericCoordinate.build(5, 5, -5);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Value can not be negative");
            assertNull(coord);
        }
    }

    @Test
    public void testAsCartesianCoordinate() {
        final double phi = 60 * Math.PI/180;
        final double theta = 120 * Math.PI/180;
        final double radius = 20 * Math.PI/180;

        final Coordinate spheric = SphericCoordinate.build(phi, theta, radius);

        final double x = radius * Math.sin(theta) * Math.cos(phi);
        final double y = radius * Math.sin(theta) * Math.sin(phi);
        final double z = radius * Math.cos(theta);
        final CartesianCoordinate expected = CartesianCoordinate.build(x, y, z);

        final CartesianCoordinate actual = spheric.asCartesianCoordinate();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCartesianDistance() {
        final CartesianCoordinate cartCoord1 = CartesianCoordinate.build(5, 10, 15);
        final CartesianCoordinate cartCoord2 = CartesianCoordinate.build(10, 15, 20);

        final SphericCoordinate cartCoord1AsSphere = cartCoord1.asSphericCoordinate();
        final SphericCoordinate cartCoord2AsSphere = cartCoord2.asSphericCoordinate();

        final double expected = cartCoord1.getCartesianDistance(cartCoord2);

        final double actual = cartCoord1AsSphere.getCartesianDistance(cartCoord2AsSphere);

        assertEquals(expected, actual, DOUBLE_COMPARE_DELTA);
    }

    @Test
    public void testAsSphericCoordinate() {
        final SphericCoordinate expected = SphericCoordinate.build(Math.toRadians(60), Math.toRadians(120), 20.0);
        final SphericCoordinate actual = expected.asSphericCoordinate();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCentralAngle() {
        final SphericCoordinate coord1 = SphericCoordinate.build(Math.toRadians(60), Math.toRadians(120), 20.0);
        final SphericCoordinate coord2 = SphericCoordinate.build(Math.toRadians(30), Math.toRadians(60), 10.0);

        final double sin = Math.sin(coord1.getPhi()) * Math.sin(coord2.getPhi());
        final double deltaTheta = Math.abs(coord2.getTheta()-coord1.getTheta());
        final double cos = Math.cos(coord1.getPhi()) * Math.cos(coord2.getPhi()) * Math.cos(deltaTheta);
        final double expected = Math.acos(sin + cos);

        final double actual = coord1.getCentralAngle(coord2);

        assertEquals(expected, actual);
    }

    @Test
    void testIsEqualTrue() {
        final SphericCoordinate coord1 = SphericCoordinate.build(5, 5, 5);
        final SphericCoordinate coord2 = SphericCoordinate.build(5, 5, 5);

        assertTrue(coord1.isEqual(coord2));
    }

    @Test
    void testIsEqualFalse() {
        final SphericCoordinate coord1 = SphericCoordinate.build(5, 5, 5);
        final SphericCoordinate coord2 = SphericCoordinate.build(5, 5, 5);

        assertTrue(coord1.isEqual(coord2));

        final SphericCoordinate coord3 = SphericCoordinate.build(6, 5, 5);
        assertFalse(coord1.isEqual(coord3));

        final SphericCoordinate coord4 = SphericCoordinate.build(5, 6, 5);
        assertFalse(coord1.isEqual(coord4));

        final SphericCoordinate coord5 = SphericCoordinate.build(5, 5, 6);
        assertFalse(coord1.isEqual(coord5));

        assertFalse(coord1.isEqual(null));
    }

    @Test
    public void testIsEqualWithFloatingValues() {
        final double y = 10;
        final double z = 10;

        double x1 = .0;
        for (int i = 1; i <= 11; i++) {
            x1 += .1;
        }
        double x2 = .1 * 11;

        final SphericCoordinate coord1 = SphericCoordinate.build(x1, y, z);
        final SphericCoordinate coord2 = SphericCoordinate.build(x2, y, z);

        assertTrue(coord1.isEqual(coord2));
    }

    @Test
    public void testEqualsTrue() {
        final SphericCoordinate coord1 = SphericCoordinate.build(5, 5, 5);
        final SphericCoordinate coord2 = SphericCoordinate.build(5, 5, 5);

        assertEquals(coord2, coord1);
        assertEquals(coord1, coord1);
    }

    @Test
    public void testEqualsFalse() {
        final SphericCoordinate coord1 = SphericCoordinate.build(5, 5, 5);
        final SphericCoordinate coord2 = SphericCoordinate.build(5, 6, 5);

        assertNotEquals(coord1, coord2);
        assertNotEquals(coord1, new Object());
        assertNotEquals(coord1, null);
    }

    @Test
    public void testValueAlreadyExists() throws NoSuchFieldException, IllegalAccessException {
        final SphericCoordinate coord1 = SphericCoordinate.build(5, 5, 5);
        final SphericCoordinate coord2 = SphericCoordinate.build(6, 5, 5);
        final SphericCoordinate coord3 = SphericCoordinate.build(5, 5, 5);

        final Field valuesField = SphericCoordinate.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        final Object valuesFieldValue = valuesField.get(null);

        if (valuesFieldValue instanceof HashMap) {
            assertEquals(((HashMap<?, ?>) valuesFieldValue).size(), 2);
        } else {
            fail("Values field has to be a HashMap");
        }
    }

}
