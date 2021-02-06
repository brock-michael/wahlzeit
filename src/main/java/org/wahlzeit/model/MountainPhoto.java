package org.wahlzeit.model;

import org.wahlzeit.model.mountain.Mountain;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MountainPhoto/Mountain Collaboration:
 * - MountainPhoto binds role Client
 * - Mountain binds role Service
 */

/**
 * Method Calls:
 * - ObjectManager calls createObject() with a ResultSet in MountainPhotoManager.
 * - MountainPhotoManager calls createPhoto() with a ResultSet in MountainPhotoFactory
 * - Alternative: PhotoUtil calls createPhoto with a PhotoId in MountainPhotoFactory
 * - MountainPhotoFactory creates new Instance of MountainPhoto with a prior specified Mountain-Object and
 * the ResultSet or alternative the PhotoId
 *
 * Object Creation Table:
 * 1. Delegation: seperate-object (MountainPhotoFactory)
 * 2. Selection: by-subclassing (From class Photo)
 * 3. Configuration: in-code (no config files or annotations used)
 * 4. Instantiation: in-code (constructor from MountainPhotoFactory directly called)
 * 5. Initialization: by-fixed-signature (constructors have fixed parameters)
 * 6. Building: default (MountainPhoto class creates dependent objects)
 */
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
