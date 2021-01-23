package org.wahlzeit.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wahlzeit.model.mountain.MountainManager;
import org.wahlzeit.model.mountain.MountainType;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MountainPhotoFactoryTest {

    private PhotoFactory photoFactory = null;

    @BeforeEach
    public void setUp() {
        this.photoFactory = MountainPhotoFactory.getInstance();
        assertTrue(photoFactory instanceof MountainPhotoFactory);
    }

    @Test
    public void testCreatePhoto() {
        final Photo photo = photoFactory.createPhoto();
        assertTrue(photo instanceof MountainPhoto);
    }

    @Test
    public void testCreatePhotoFromId() {
        final Photo photo = photoFactory.createPhoto(new PhotoId(0));
        assertTrue(photo instanceof MountainPhoto);
    }

    @Test
    public void testCreatePhotoFromResultSet() throws SQLException {
        final ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.getString("owner_email_address")).thenReturn("test@fau.de");
        Mockito.when(resultSetMock.getString("owner_home_page")).thenReturn("http://www.test-fau.de");

        final Photo photo = photoFactory.createPhoto(resultSetMock);

        assertTrue(photo instanceof MountainPhoto);
    }

    @Test
    public void testDefaultMountain() {
        final MountainPhoto photo = (MountainPhoto) photoFactory.createPhoto();
        assertNotNull(photo);
        assertNotNull(photo.getMountain());
        assertTrue(MountainManager.instance.getAllMountainType().containsKey("volcanic"));
    }

    @Test
    public void testSetMountain() {
        final String typeName = "plateau";
        this.photoFactory.setType(typeName);
        final MountainPhoto photo = (MountainPhoto) photoFactory.createPhoto();
        assertNotNull(photo);
        assertNotNull(photo.getMountain());
        assertTrue(MountainManager.instance.getAllMountainType().containsKey(typeName));
    }

}
