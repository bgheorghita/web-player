package dev.gb.webplayerserver.services.creators.impl;

import dev.gb.webplayerserver.exceptions.BadRequestException;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.repositories.creators.ArtistRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtistServiceImpTest {

    private ArtistServiceImp artistServiceUnderTest;
    private final ArtistRepository artistRepository = mock(ArtistRepository.class);

    @BeforeEach
    void setUp() {
        artistServiceUnderTest = new ArtistServiceImp(artistRepository);
    }

    @Test
    void createArtist_ShouldThrowBadRequestException_WhenOwnerIsMissing() {
        String owner = "";
        CustomDesign customDesign = new CustomDesign();

        try (MockedStatic<StringUtils> stringUtilsMock = mockStatic(StringUtils.class)) {
            stringUtilsMock.when(() -> StringUtils.isEmpty(owner)).thenReturn(true);

            assertThrows(BadRequestException.class, () -> artistServiceUnderTest.createArtist(owner, customDesign));
        }
    }

    @Test
    void createArtist_ShouldThrowBadRequestException_WhenNameInCustomDesignIsMissing() {
        String owner = "";
        CustomDesign customDesign = new CustomDesign();

        try (MockedStatic<StringUtils> stringUtilsMock = mockStatic(StringUtils.class)) {
            stringUtilsMock.when(() -> StringUtils.isEmpty(owner)).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getName())).thenReturn(true);

            assertThrows(BadRequestException.class, () -> artistServiceUnderTest.createArtist(owner, customDesign));
        }
    }

    @Test
    void createArtist_ShouldUseDefaultValueForCoverImagePath_WhenCoverImagePathIsNotProvided() {
        String owner = "OWNER_MOCK";
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("NAME_MOCK");

        try (MockedStatic<StringUtils> stringUtilsMock = mockStatic(StringUtils.class)) {
            stringUtilsMock.when(() -> StringUtils.isEmpty(owner)).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getName())).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getCoverImagePath())).thenReturn(true);
        }

        artistServiceUnderTest.createArtist(owner, customDesign);
        assertEquals(CustomDesign.DEFAULT_COVER_IMG_PATH, customDesign.getCoverImagePath());
    }

    @Test
    void createArtist_ShouldUseProvidedValueForCoverImagePath_WhenCoverImagePathIsProvided() {
        String owner = "OWNER_MOCK";
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("NAME_MOCK");
        String coverImage = "COVER_IMAGE";
        customDesign.setCoverImagePath(coverImage);

        try (MockedStatic<StringUtils> stringUtilsMock = mockStatic(StringUtils.class)) {
            stringUtilsMock.when(() -> StringUtils.isEmpty(owner)).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getName())).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getCoverImagePath())).thenReturn(false);
        }

        artistServiceUnderTest.createArtist(owner, customDesign);
        assertEquals(coverImage, customDesign.getCoverImagePath());
    }

    @Test
    void createArtist_ShouldUseProvidedValuesForCoverImagePath_And_ProfileImagePath_WhenTheyAreProvided() {
        String owner = "OWNER_MOCK";
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("NAME_MOCK");
        String coverImage = "COVER_IMAGE";
        String profileImage = "PROFILE_IMAGE";
        customDesign.setCoverImagePath(coverImage);
        customDesign.setProfileImagePath(profileImage);

        try (MockedStatic<StringUtils> stringUtilsMock = mockStatic(StringUtils.class)) {
            stringUtilsMock.when(() -> StringUtils.isEmpty(owner)).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getName())).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getCoverImagePath())).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getCoverImagePath())).thenReturn(false);
        }

        artistServiceUnderTest.createArtist(owner, customDesign);
        assertEquals(coverImage, customDesign.getCoverImagePath());
        assertEquals(profileImage, customDesign.getProfileImagePath());
    }

    @Test
    void createArtist_ShouldUseDefaultValuesForCoverImagePath_And_ProfileImagePath_WhenTheyAreNotProvided() {
        String owner = "OWNER_MOCK";
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("NAME_MOCK");

        try (MockedStatic<StringUtils> stringUtilsMock = mockStatic(StringUtils.class)) {
            stringUtilsMock.when(() -> StringUtils.isEmpty(owner)).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getName())).thenReturn(false);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getCoverImagePath())).thenReturn(true);
            stringUtilsMock.when(() -> StringUtils.isEmpty(customDesign.getCoverImagePath())).thenReturn(true);
        }

        artistServiceUnderTest.createArtist(owner, customDesign);
        assertEquals(CustomDesign.DEFAULT_COVER_IMG_PATH, customDesign.getCoverImagePath());
        assertEquals(CustomDesign.DEFAULT_PROFILE_IMAGE_PATH, customDesign.getProfileImagePath());
    }
}