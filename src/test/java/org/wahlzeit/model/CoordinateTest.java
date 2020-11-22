package org.wahlzeit.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    public void testGetDistanceWithIllegalInput() throws Exception {
        final Coordinate coord1 = new Coordinate(5, 5, 5);
        Double returnValue = null;

        try {
            returnValue = coord1.getDistance(null);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Cant be null");
            assertNull(returnValue);
        }
    }

    @Test
    public void testGetDistance() {
        final double x1 = 5, x2 = 10;
        final double y1 = 5, y2 = 10;
        final double z1 = 5, z2 = 10;
        final Coordinate coord1 = new Coordinate(x1, y1, z1);
        final Coordinate coord2 = new Coordinate(x2, y2, z2);

        final double calcDist1 = coord2.getDistance(coord1);
        final double calcDist2 = coord2.getDistance(coord1);
        final double realDist = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2) + Math.pow(z2-z1, 2));

        assertEquals(realDist, calcDist1);
        assertEquals(realDist, calcDist2);
    }

    @Test
    void testIsEqualTrue() {
        final Coordinate coord1 = new Coordinate(5, 5, 5);
        final Coordinate coord2 = new Coordinate(5, 5, 5);

        assertTrue(coord1.isEqual(coord2));
    }

    @Test
    void testIsEqualFalse() {
        final Coordinate coord1 = new Coordinate(5, 5, 5);
        final Coordinate coord2 = new Coordinate(5, 5, 5);

        assertTrue(coord1.isEqual(coord2));

        coord2.setX(coord2.getX()+1);
        assertFalse(coord1.isEqual(coord2));

        coord2.setX(coord1.getX());
        coord2.setY(coord2.getY()+1);
        assertFalse(coord1.isEqual(coord2));

        coord2.setY(coord1.getY());
        coord2.setZ(coord2.getZ()+1);
        assertFalse(coord1.isEqual(coord2));

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

        final Coordinate coord1 = new Coordinate(x1, y, z);
        final Coordinate coord2 = new Coordinate(x2, y, z);

        assertTrue(coord1.isEqual(coord2));
    }

    @Test
    public void testEqualsTrue() {
        final Coordinate coord1 = new Coordinate(5, 5, 5);
        final Coordinate coord2 = new Coordinate(5, 5, 5);

        assertEquals(coord2, coord1);
        assertEquals(coord1, coord1);
    }

    @Test
    public void testEqualsFalse() {
        final Coordinate coord1 = new Coordinate(5, 5, 5);
        final Coordinate coord2 = new Coordinate(5, 6, 5);

        assertNotEquals(coord1, coord2);
        assertNotEquals(coord1, new Object());
        assertNotEquals(coord1, null);
    }
}