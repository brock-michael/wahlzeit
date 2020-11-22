package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MountainPhoto extends Photo {

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
        this.mountainHeight = rset.getInt("mountain_height");
        this.mountainLocation = rset.getString("mountain_location");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        super.writeOn(rset);
        rset.updateInt("mountain_height", this.mountainHeight);
        rset.updateString("mountain_location", this.mountainLocation);
    }

    public int getMountainHeight() {
        return mountainHeight;
    }

    public String getMountainLocation() {
        return mountainLocation;
    }
}
