package org.wahlzeit.model;

import org.wahlzeit.model.location.AssertionUtils;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"ConcreteProduct"}
)
public class MountainPhoto extends Photo {

    private static final String LABEL_MOUNTAIN_HEIGHT = "mountain_height";
    private static final String LABEL_MOUNTAIN_LOCATION = "mountain_location";

    private int mountainHeight = 0;
    private String mountainLocation = "-";


    public MountainPhoto() {
        super();
    }

    public MountainPhoto(PhotoId myId) {
        super(myId);
    }

    public MountainPhoto(ResultSet rset) throws SQLException {
        this.readFrom(rset);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);

        final int mountain_height = rset.getInt(LABEL_MOUNTAIN_HEIGHT);

        try {
            AssertionUtils.assertNotNegative(mountain_height);
            this.mountainHeight = mountain_height;
        } catch (IllegalStateException e) {
            this.mountainHeight = 0;
        }

        this.mountainLocation = rset.getString(LABEL_MOUNTAIN_LOCATION);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        super.writeOn(rset);

        try {
            AssertionUtils.assertNotNegative(this.mountainHeight);
            rset.updateInt(LABEL_MOUNTAIN_HEIGHT, this.mountainHeight);
        } catch (IllegalStateException e) {
            // mountainHeightToSet stays as before
        }

        rset.updateString(LABEL_MOUNTAIN_LOCATION, this.mountainLocation);
    }

    public int getMountainHeight() {
        return mountainHeight;
    }

    public String getMountainLocation() {
        return mountainLocation;
    }
}
