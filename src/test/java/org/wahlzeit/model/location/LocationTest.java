package org.wahlzeit.model.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void testBuilder() {
        final double x = 5;
        final double y = 6;
        final double z = 7;

        final Location loc = Location.buildWithCartesianCoord(x, y, z);
        assertEquals(loc.getCoordinate(), CartesianCoordinate.build(x, y, z));
    }

    @Test
    public void testSetCartCoordinate() {
        final Location loc = Location.buildWithCartesianCoord(5, 6, 7);

        final CartesianCoordinate newCartesianCoordinate = CartesianCoordinate.build(10, 12, 14);
        loc.setCartesianCoord(newCartesianCoordinate.getX(), newCartesianCoordinate.getY(), newCartesianCoordinate.getZ());

        assertEquals(loc.getCoordinate(), newCartesianCoordinate);
    }

    @Test
    public void testCreateInvalidCartesianCoordinate() {
        final Location loc = Location.buildWithCartesianCoord(Double.NaN, 234.3, 84.5);

        final CartesianCoordinate expectedCoord = CartesianCoordinate.build(0, 0, 0);

        assertEquals(loc.getCoordinate(), expectedCoord);
    }

    @Test
    public void testCreateInvalidSphericCoordinate() {
        final Location loc = Location.buildWithSphericCoord(Double.NaN, 234.3, 84.5);

        final SphericCoordinate expectedCoord = SphericCoordinate.build(0, 0, 0);

        assertEquals(loc.getCoordinate(), expectedCoord);
    }

}
