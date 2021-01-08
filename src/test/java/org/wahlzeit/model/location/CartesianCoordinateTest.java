package org.wahlzeit.model.location;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CartesianCoordinateTest {

    @Test
    public void testInitWithInvalidParam() throws Exception {
        CartesianCoordinate coord = null;

        try {
            coord = CartesianCoordinate.build(Double.NaN, Double.NaN, Double.NaN);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Double value has to be a number");
            assertNull(coord);
        }
    }

    @Test
    public void testAsCartesianCoordinate() {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 10, 15);
        assertEquals(coord1, coord1.asCartesianCoordinate());
    }

    @Test
    public void testGetDistanceWithIllegalInput() throws Exception {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 5, 5);
        Double returnValue = null;

        try {
            returnValue = coord1.getCartesianDistance(null);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter can not be null");
            assertNull(returnValue);
        }
    }

    @Test
    public void testGetDistance() {
        final double x1 = 5, x2 = 10;
        final double y1 = 5, y2 = 10;
        final double z1 = 5, z2 = 10;
        final CartesianCoordinate coord1 = CartesianCoordinate.build(x1, y1, z1);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(x2, y2, z2);

        final double calcDist1 = coord2.getCartesianDistance(coord1);
        final double calcDist2 = coord1.getCartesianDistance(coord2);
        final double realDist = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2) + Math.pow(z2-z1, 2));

        assertEquals(realDist, calcDist1);
        assertEquals(realDist, calcDist2);
    }

    @Test
    public void testAsSphericCoordinate() {
        final double x = 5;
        final double y = 10;
        final double z = 15;

        final double phi = Math.atan2(y, x);
        final double radius = Math.sqrt(x*x + y*y + z*z);
        final double theta = Math.acos(z / radius);
        final SphericCoordinate expectedCoordinate = SphericCoordinate.build(phi, radius, theta);

        final Coordinate coordinate = CartesianCoordinate.build(x, y, z);
        assertEquals(expectedCoordinate, coordinate.asSphericCoordinate());
    }

    @Test
    public void testGetCentralAngle() {
        final double x1 = 5, x2 = 5;
        final double y1 = 10, y2 = 10;
        final double z1 = 15, z2 = 15;
        final Coordinate coord1 = CartesianCoordinate.build(x1, y1, z1);
        final Coordinate coord2 = CartesianCoordinate.build(x2, y2, z2);

        final double phi1 = Math.atan2(y1, x1);
        final double radius1 = Math.sqrt(x1*x1 + y1*y1 + z1*z1);
        final double theta1 = Math.acos(z1 / radius1);

        final double phi2 = Math.atan2(y2, x2);
        final double radius2 = Math.sqrt(x2*x2 + y2*y2 + z2*z2);
        final double theta2 = Math.acos(z2 / radius2);

        final double sin = Math.sin(phi1) * Math.sin(phi2);
        final double deltaTheta = Math.abs(theta1-theta2);
        final double cos = Math.cos(phi1) * Math.cos(phi2) * Math.cos(deltaTheta);
        final double expectedCentralAngle = Math.acos(sin + cos);

        final double actualCentralAngle = coord1.getCentralAngle(coord2);

        assertEquals(expectedCentralAngle, actualCentralAngle);
    }

    @Test
    void testIsEqualTrue() {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 5, 5);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(5, 5, 5);

        assertTrue(coord1.isEqual(coord2));
    }

    @Test
    void testIsEqualFalse() {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 5, 5);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(5, 5, 5);

        assertTrue(coord1.isEqual(coord2));

        final CartesianCoordinate coord3 = CartesianCoordinate.build(6, 5, 5);
        assertFalse(coord1.isEqual(coord3));

        final CartesianCoordinate coord4 = CartesianCoordinate.build(5, 6, 5);
        assertFalse(coord1.isEqual(coord4));

        final CartesianCoordinate coord5 = CartesianCoordinate.build(5, 5, 6);
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

        final CartesianCoordinate coord1 = CartesianCoordinate.build(x1, y, z);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(x2, y, z);

        assertTrue(coord1.isEqual(coord2));
    }

    @Test
    public void testEqualsTrue() {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 5, 5);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(5, 5, 5);

        assertEquals(coord2, coord1);
        assertEquals(coord1, coord1);
    }

    @Test
    public void testEqualsFalse() {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 5, 5);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(5, 6, 5);

        assertNotEquals(coord1, coord2);
        assertNotEquals(coord1, new Object());
        assertNotEquals(coord1, null);
    }

    @Test
    public void testValueAlreadyExists() throws NoSuchFieldException, IllegalAccessException {
        final CartesianCoordinate coord1 = CartesianCoordinate.build(5, 5, 5);
        final CartesianCoordinate coord2 = CartesianCoordinate.build(6, 5, 5);
        final CartesianCoordinate coord3 = CartesianCoordinate.build(5, 5, 5);

        final Field valuesField = CartesianCoordinate.class.getDeclaredField("values");
        valuesField.setAccessible(true);
        final Object valuesFieldValue = valuesField.get(null);

        if (valuesFieldValue instanceof HashMap) {
            assertEquals(((HashMap<?, ?>) valuesFieldValue).size(), 2);
        } else {
            fail("Values field has to be a HashMap");
        }
    }

}
