package org.wahlzeit.model.location;

public class CompareUtils {
    private static final double COMPARE_THRESHOLD = .0001;

    public static boolean compareDouble(final double d1, final double d2) {
        return Math.abs(d1 - d2) < COMPARE_THRESHOLD;
    }
}
