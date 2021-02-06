package org.wahlzeit.model.mountain;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * MountainManager/Mountain Collaboration:
 * - MountainManager binds role Manager
 * - Mountain binds role Element
 * - MountainPhotoFactory binds role Client
 */

public class MountainManager extends ObjectManager {

    public static final MountainManager instance = new MountainManager();

    private final Map<String, MountainType> allMountainType = new HashMap<>();
    private final Map<Integer, Mountain> allMountain = new HashMap<>();

    private MountainManager() {}

    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        final Mountain mountain = this.createMountain("no-type");
        mountain.readFrom(rset);
        return mountain;
    }

    public Mountain createMountainAsSubtype(final String typeName, final String parentTypeName) {
        this.assertValidType(typeName);
        this.assertValidType(parentTypeName);

        final Mountain createdMountain = this.createMountain(typeName);

        MountainType parentType = this.allMountainType.get(parentTypeName);
        if (parentType == null) {
            parentType = new MountainType();
            this.allMountainType.put(parentTypeName, parentType);
        }
        parentType.addSubType(this.allMountainType.get(typeName));

        return createdMountain;
    }

    public Mountain createMountain(final String typeName) {
        this.assertValidType(typeName);
        MountainType mountainType = this.allMountainType.get(typeName);
        if (mountainType == null) {
            mountainType = new MountainType();
            this.allMountainType.put(typeName, mountainType);
        }

        final Mountain newMountain = mountainType.createInstance();
        allMountain.put(newMountain.hashCode(), newMountain);
        return newMountain;
    }

    private void assertValidType(final String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            throw new IllegalArgumentException("Typename can not be empty");
        }
    }

    public Map<String, MountainType> getAllMountainType() {
        return allMountainType;
    }
}
