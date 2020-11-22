package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MountainPhotoManager extends PhotoManager {

    public MountainPhotoManager() {
        photoTagCollector = MountainPhotoFactory.getInstance().createPhotoTagCollector();
    }

    @Override
    protected Photo createObject(ResultSet rset) throws SQLException {
        return MountainPhotoFactory.getInstance().createPhoto(rset);
    }
}
