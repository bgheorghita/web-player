package dev.gb.webplayerserver.services.concrete.creators.impl;

import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.repositories.creators.PodcasterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PodcasterServiceImpIntegrationTest {
    @Autowired
    private PodcasterServiceImp podcasterServiceImp;
    @Autowired
    private PodcasterRepository podcasterRepository;

    private Podcaster savedPodcaster;

    @BeforeEach
    void setUp() {
        savedPodcaster = new Podcaster();
        podcasterServiceImp.save(savedPodcaster);
    }

    @AfterEach
    void tearDown() {
        podcasterRepository.deleteAll();
    }

    @Test
    void save_ShouldPersistPodcaster(){
        Podcaster retrievedPodcaster = podcasterServiceImp.findById(savedPodcaster.getId()).orElse(null);
        assertNotNull(retrievedPodcaster);
    }

    @Test
    void save_ShouldUpdatePodcaster_WhenDetailsAreAdded(){
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("NAME");
        customDesign.setProfileImagePath("P_IM");
        customDesign.setCoverImagePath("C_IM");

        savedPodcaster.setCustomDesign(customDesign);
        savedPodcaster.setJoinedDate(LocalDate.now());

        podcasterServiceImp.save(savedPodcaster);
        Podcaster retrievedPodcaster = podcasterServiceImp.findById(savedPodcaster.getId()).orElse(null);

        assertNotNull(retrievedPodcaster);
        assertEquals(savedPodcaster, retrievedPodcaster);
        assertEquals(customDesign, retrievedPodcaster.getCustomDesign());
    }

    @Test
    void delete_ShouldRemovePodcaster_WhenPodcasterDoesNotHaveAnyPodcast(){
        podcasterServiceImp.delete(savedPodcaster);
        Podcaster retrievedPodcaster = podcasterServiceImp.findById(savedPodcaster.getId()).orElse(null);
        assertNull(retrievedPodcaster);
    }
}