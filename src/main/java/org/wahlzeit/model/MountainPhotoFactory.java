package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MountainPhotoFactory extends PhotoFactory {

    @Override
    public Photo createPhoto() {
        return new MountainPhoto();
    }

    @Override
    public Photo createPhoto(PhotoId id) {
        return new MountainPhoto(id);
    }

    @Override
    public Photo createPhoto(ResultSet rs) throws SQLException {
        return new MountainPhoto(rs);
    }
}
