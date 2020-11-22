package org.wahlzeit.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MountainPhotoManagerTest {
    private static final String MOCK_RS_OWNER_MAIL = "test@fau.de";
    private static final String MOCK_RS_OWNER_HOME_PAGE = "http://www.test-fau.de";
    private static final int MOCK_RS_MOUNTAIN_HEIGHT = 500;
    private static final String MOCK_RS_MOUNTAIN_LOCATION = "Erlangen";

    @Test
    public void testPhotoTagCollectorInit() {
        final PhotoManager photoManager = new MountainPhotoManager();
        assertNotNull(photoManager.photoTagCollector);
    }

    @Test
    public void testCreateObject() throws SQLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.getString("owner_email_address")).thenReturn(MOCK_RS_OWNER_MAIL);
        Mockito.when(resultSetMock.getString("owner_home_page")).thenReturn(MOCK_RS_OWNER_HOME_PAGE);
        Mockito.when(resultSetMock.getInt("mountain_height")).thenReturn(MOCK_RS_MOUNTAIN_HEIGHT);
        Mockito.when(resultSetMock.getString("mountain_location")).thenReturn(MOCK_RS_MOUNTAIN_LOCATION);

        final PhotoManager photoManager = new MountainPhotoManager();
        final Photo photo = photoManager.createObject(resultSetMock);

        assertTrue(photo instanceof MountainPhoto);
        assertEquals(photo.ownerEmailAddress.asString(), MOCK_RS_OWNER_MAIL);
        assertEquals(photo.ownerHomePage.toString(), MOCK_RS_OWNER_HOME_PAGE);
        assertEquals(((MountainPhoto) photo).getMountainHeight(), MOCK_RS_MOUNTAIN_HEIGHT);
        assertEquals(((MountainPhoto) photo).getMountainLocation(), MOCK_RS_MOUNTAIN_LOCATION);
    }

}