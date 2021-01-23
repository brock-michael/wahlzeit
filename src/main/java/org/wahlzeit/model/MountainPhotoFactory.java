package org.wahlzeit.model;

import org.wahlzeit.model.mountain.Mountain;
import org.wahlzeit.model.mountain.MountainManager;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"ConcreteFactory"}
)
public class MountainPhotoFactory extends PhotoFactory implements FactoryWithType {

    private Mountain mountain = MountainManager.instance.createMountain("volcanic");

    @Override
    public Photo createPhoto() {
        return new MountainPhoto(mountain);
    }

    @Override
    public Photo createPhoto(PhotoId id) {
        return new MountainPhoto(mountain, id);
    }

    @Override
    public Photo createPhoto(ResultSet rs) throws SQLException {
        return new MountainPhoto(mountain, rs);
    }

    @Override
    public void setType(final String mountainName) {
        this.mountain = MountainManager.instance.createMountain(mountainName);
    }
}
