package org.wahlzeit.model;

import org.junit.jupiter.api.Test;
import org.wahlzeit.model.location.CartesianCoordinate;
import org.wahlzeit.model.location.Location;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void testBuilder() {
        final double x = 5;
        final double y = 6;
        final double z = 7;

        final Location loc = Location.buildWithCartesianCoord(x, y, z);
        assertEquals(loc.getCoordinate(), new CartesianCoordinate(x, y, z));
    }

    @Test
    public void testSetCartCoordinate() {
        final Location loc = Location.buildWithCartesianCoord(5, 6, 7);

        final CartesianCoordinate newCartesianCoordinate = new CartesianCoordinate(10, 12, 14);
        loc.setCartesianCoord(newCartesianCoordinate.getX(), newCartesianCoordinate.getY(), newCartesianCoordinate.getZ());

        assertEquals(loc.getCoordinate(), newCartesianCoordinate);
    }

}