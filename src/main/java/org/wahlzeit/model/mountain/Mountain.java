package org.wahlzeit.model.mountain;

import org.wahlzeit.model.location.AssertionUtils;
import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Mountain extends DataObject {
    private static final String LABEL_MOUNTAIN_HEIGHT = "mountain_height";
    private static final String LABEL_MOUNTAIN_LOCATION = "mountain_location";

    private final MountainType type;

    private int height = 0;
    private String location = "-";

    public Mountain(final MountainType type) {
        this.type = type;
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        final int mountain_height = rset.getInt(LABEL_MOUNTAIN_HEIGHT);

        try {
            AssertionUtils.assertNotNegative(mountain_height);
            this.height = mountain_height;
        } catch (IllegalStateException e) {
            this.height = 0;
        }

        this.location = rset.getString(LABEL_MOUNTAIN_LOCATION);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        try {
            AssertionUtils.assertNotNegative(this.height);
            rset.updateInt(LABEL_MOUNTAIN_HEIGHT, this.height);
        } catch (IllegalStateException e) {
            // mountainHeightToSet stays as before
        }

        rset.updateString(LABEL_MOUNTAIN_LOCATION, this.location);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mountain mountain = (Mountain) o;
        return height == mountain.height &&
                Objects.equals(type, mountain.type) &&
                Objects.equals(location, mountain.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, height, location);
    }

    public MountainType getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public String getLocation() {
        return location;
    }
}
