package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"ConcreteFactory"}
)
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
