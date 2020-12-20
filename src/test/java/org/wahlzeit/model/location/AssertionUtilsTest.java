package org.wahlzeit.model.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssertionUtilsTest {

    @Test
    public void testAssertDoubleNaN() throws Exception {
        try {
            AssertionUtils.assertDouble(Double.NaN);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalStateException e) {
            assertEquals("Double value has to be a number", e.getMessage());
        }
    }

    @Test
    public void testAssertDoubleInfinite() throws Exception {
        try {
            AssertionUtils.assertDouble(Double.POSITIVE_INFINITY);
            throw new Exception("That shouldnt have happened :(");
        } catch (IllegalStateException e) {
            assertEquals("Double value can not be infinite", e.getMessage());
        }
    }

    @Test
    public void testAssertNotNegative() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            AssertionUtils.assertNotNegative(-0.5);
        });
    }

    @Test
    public void testAssertNotNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AssertionUtils.assertNotNull(null);
        });
    }

    @Test
    public void testAssertCentralAngle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AssertionUtils.assertCentralAngle(-0.5);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AssertionUtils.assertCentralAngle(3.15);
        });
    }

}