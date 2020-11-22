package org.wahlzeit.model;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MountainPhotoManagerTest {

    @Test
    public void testPhotoTagCollectorInit() {
        final PhotoManager photoManager = new MountainPhotoManager();
        assertNotNull(photoManager.photoTagCollector);
    }

    @Test
    public void testCreateObject() throws SQLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.getString("owner_email_address")).thenReturn("test@fau.de");
        Mockito.when(resultSetMock.getString("owner_home_page")).thenReturn("http://www.test-fau.de");

        final PhotoManager photoManager = new MountainPhotoManager();
        final Photo photo = photoManager.createObject(resultSetMock);

        assertTrue(photo instanceof MountainPhoto);
    }

}