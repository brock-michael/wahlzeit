package org.wahlzeit.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wahlzeit.model.mountain.Mountain;
import org.wahlzeit.model.mountain.MountainManager;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MountainPhotoTest {
    private static final String MOCK_RS_OWNER_EMAIL = "test@fau.de";
    private static final String MOCK_RS_OWNER_HOME_PAGE = "http://www.test-fau.de";
    private static final int MOCK_RS_MOUNTAIN_HEIGHT = 3000;
    private static final String MOCK_RS_MOUNTAIN_LOCATION = "Austria";

    private static final Mountain mountain = MountainManager.instance.createMountain("volcanic");

    @Test
    public void testCreateSimple() {
        final MountainPhoto mountainPhoto1 = new MountainPhoto(null);
        final MountainPhoto mountainPhoto2 = new MountainPhoto(null, new PhotoId(5));

        assertTrue(mountainPhoto1.id.isEqual(new PhotoId(1)));
        assertTrue(mountainPhoto2.id.isEqual(new PhotoId(5)));
    }

    @Test
    public void testCreateWithResultSet() throws SQLException {
        final ResultSet resultSetMock = setUpReadResultSetMock();

        final MountainPhoto mountainPhoto = new MountainPhoto(mountain, resultSetMock);

        assertEquals(mountainPhoto.ownerEmailAddress.asString(), MOCK_RS_OWNER_EMAIL);
        assertEquals(mountainPhoto.ownerHomePage.toString(), MOCK_RS_OWNER_HOME_PAGE);
        assertEquals(mountainPhoto.getMountain().getHeight(), MOCK_RS_MOUNTAIN_HEIGHT);
        assertEquals(mountainPhoto.getMountain().getLocation(), MOCK_RS_MOUNTAIN_LOCATION);
    }

    @Test
    public void testReadFrom() throws SQLException {
        final ResultSet resultSetMock = setUpReadResultSetMock();

        final MountainPhoto mountainPhoto = new MountainPhoto(mountain);

        assertEquals(mountainPhoto.getMountain().getHeight(), 0);
        assertEquals(mountainPhoto.getMountain().getLocation(), "-");

        mountainPhoto.readFrom(resultSetMock);

        assertEquals(mountainPhoto.getMountain().getHeight(), MOCK_RS_MOUNTAIN_HEIGHT);
        assertEquals(mountainPhoto.getMountain().getLocation(), MOCK_RS_MOUNTAIN_LOCATION);
    }

    @Test
    public void testReadFromWithInvalidMountainHeight() throws SQLException, NoSuchFieldException, IllegalAccessException {
        final ResultSet resultSetMock = setUpReadResultSetMock();
        when(resultSetMock.getInt("mountain_height")).thenReturn(-1);

        final MountainPhoto mountainPhoto = new MountainPhoto(mountain);

        mountainPhoto.readFrom(resultSetMock);

        assertEquals(0, mountainPhoto.getMountain().getHeight());
    }

    @Test
    public void testWriteOn() throws SQLException, NoSuchFieldException, IllegalAccessException, MalformedURLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        final MountainPhoto mountainPhoto = new MountainPhoto(mountain);
        mountainPhoto.ownerHomePage = new URL(MOCK_RS_OWNER_HOME_PAGE);
        this.setPrivateField(mountainPhoto, "mountainHeight", MOCK_RS_MOUNTAIN_HEIGHT);
        this.setPrivateField(mountainPhoto, "mountainLocation", MOCK_RS_MOUNTAIN_LOCATION);

        mountainPhoto.writeOn(resultSetMock);

        verify(resultSetMock).updateInt("mountain_height", MOCK_RS_MOUNTAIN_HEIGHT);
        verify(resultSetMock).updateString("mountain_location", MOCK_RS_MOUNTAIN_LOCATION);
    }

    @Test
    public void testWriteOnWithInvalidMountainHeight() throws SQLException, NoSuchFieldException, IllegalAccessException, MalformedURLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        final MountainPhoto mountainPhoto = new MountainPhoto(mountain);
        mountainPhoto.ownerHomePage = new URL(MOCK_RS_OWNER_HOME_PAGE);
        this.setPrivateField(mountainPhoto, "mountainHeight", -1);

        mountainPhoto.writeOn(resultSetMock);

        verify(resultSetMock).updateInt("mountain_height", 0);
    }

    private ResultSet setUpReadResultSetMock() throws SQLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        when(resultSetMock.getString("owner_email_address")).thenReturn(MOCK_RS_OWNER_EMAIL);
        when(resultSetMock.getString("owner_home_page")).thenReturn(MOCK_RS_OWNER_HOME_PAGE);
        when(resultSetMock.getInt("mountain_height")).thenReturn(MOCK_RS_MOUNTAIN_HEIGHT);
        when(resultSetMock.getString("mountain_location")).thenReturn(MOCK_RS_MOUNTAIN_LOCATION);

        return resultSetMock;
    }

    private void setPrivateField(final Object object, final String name, final Object value)
            throws NoSuchFieldException, IllegalAccessException {
        final Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, value);
    }
}
