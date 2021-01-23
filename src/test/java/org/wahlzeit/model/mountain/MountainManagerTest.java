package org.wahlzeit.model.mountain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MountainManagerTest {

    @Test
    public void testCreateMountain() {
        final String typeName = "volcanic";

        // First
        final Mountain mountain = MountainManager.instance.createMountain(typeName);
        assertNotNull(mountain);
        assertTrue(MountainManager.instance.getAllMountainType().containsKey(typeName));

        // Second
        final Mountain mountain2 = MountainManager.instance.createMountain(typeName);
        assertNotNull(mountain2);
        assertEquals(MountainManager.instance.getAllMountainType().size(), 1);

        assertEquals(mountain.getType(), mountain2.getType());
    }

    @Test
    public void testCreateObject() throws SQLException {
        final ResultSet resultSetMock = this.setUpReadResultSetMock();

        final Mountain mountain = (Mountain) MountainManager.instance.createObject(resultSetMock);
        assertEquals(MountainManager.instance.getAllMountainType().size(), 1);
        assertEquals(MountainManager.instance.getAllMountainType().keySet().toArray()[0], "no-type");
    }

    @Test
    public void testCreateMountainAsSubtype() {
        final String parentTypeName = "parent";
        final String typeName = "child";

        final Mountain mountain = MountainManager.instance.createMountainAsSubtype(typeName, parentTypeName);
        assertNotNull(mountain);

        final MountainType parent = MountainManager.instance.getAllMountainType().get("parent");
        final MountainType child = MountainManager.instance.getAllMountainType().get("child");
        assertTrue(child.isSubtype());
        assertEquals(child.superType, parent);
    }

    private ResultSet setUpReadResultSetMock() throws SQLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        when(resultSetMock.getString("owner_email_address")).thenReturn("test");
        when(resultSetMock.getString("owner_home_page")).thenReturn("test");
        when(resultSetMock.getInt("mountain_height")).thenReturn(0);
        when(resultSetMock.getString("mountain_location")).thenReturn("test");

        return resultSetMock;
    }
}
