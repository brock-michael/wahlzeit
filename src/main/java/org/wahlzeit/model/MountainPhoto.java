package org.wahlzeit.model;

import org.wahlzeit.model.mountain.Mountain;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"ConcreteProduct"}
)
public class MountainPhoto extends Photo {

    private final Mountain mountain;

    public MountainPhoto(final Mountain mountain) {
        super();
        this.mountain = mountain;
    }

    public MountainPhoto(final Mountain mountain, PhotoId myId) {
        super(myId);
        this.mountain = mountain;
    }

    public MountainPhoto(final Mountain mountain, ResultSet rset) throws SQLException {
        this.mountain = mountain;
        this.readFrom(rset);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);
        this.mountain.readFrom(rset);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        super.writeOn(rset);
        this.mountain.writeOn(rset);
    }

    public Mountain getMountain() {
        return this.mountain;
    }
}
