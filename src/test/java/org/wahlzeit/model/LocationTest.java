package org.wahlzeit.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void testBuilder() {
        final double x = 5;
        final double y = 6;
        final double z = 7;

        final Location loc = Location.buildWithCartesianCoord(x, y, z);
        assertEquals(loc.getCartCoordinate(), new Coordinate(x, y, z));
    }

    @Test
    public void testSetCartCoordinate() {
        final Location loc = Location.buildWithCartesianCoord(5, 6, 7);

        final Coordinate newCoordinate = new Coordinate(10, 12, 14);
        loc.setCartCoordinate(newCoordinate.getX(), newCoordinate.getY(), newCoordinate.getZ());

        assertEquals(loc.getCartCoordinate(), newCoordinate);
    }

}