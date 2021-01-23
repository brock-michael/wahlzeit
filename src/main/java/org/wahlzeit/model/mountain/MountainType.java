package org.wahlzeit.model.mountain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class MountainType {

    protected MountainType superType;
    protected Set<MountainType> allSubType = new HashSet<>();

    public Mountain createInstance() {
        return new Mountain(this);
    }

    public MountainType getSuperType() {
        return this.superType;
    }
    public Iterator<MountainType> getSubTypeIterator() {
        return allSubType.iterator();
    }

    public void setSuperType(final MountainType superType) {
        this.checkInput(superType);
        this.superType = superType;
    }

    public void addSubType(final MountainType mountainType) {
        this.checkInput(mountainType);
        mountainType.setSuperType(this);
        allSubType.add(mountainType);
    }

    public boolean hasInstance(final Mountain mountain) {
        this.checkInput(mountain);
        if (mountain.getType() == this) {
            return true;
        }

        for (MountainType type : this.allSubType) {
            if (type.hasInstance(mountain)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSubtype() {
        return this.superType != null;
    }

    private void checkInput(final Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Input can not be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MountainType that = (MountainType) o;
        return Objects.equals(superType, that.superType) &&
                Objects.equals(allSubType, that.allSubType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superType, allSubType);
    }
}
