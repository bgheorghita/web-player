package dev.gb.webplayerserver.services.concrete.audio.impl;

import dev.gb.webplayerserver.models.concrete.audio.Single;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import dev.gb.webplayerserver.repositories.audio.SingleRepository;
import dev.gb.webplayerserver.repositories.genres.TrackGenreRepository;
import dev.gb.webplayerserver.services.audio.impl.SingleServiceImp;
import dev.gb.webplayerserver.services.creators.ArtistService;
import dev.gb.webplayerserver.services.genres.GenreService;
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
class SingleServiceImpIntegrationTest {
    @Autowired
    private SingleServiceImp singleServiceImp;
    @Autowired
    private SingleRepository singleRepository;
    @Autowired
    private TrackGenreRepository trackGenreRepository;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private GenreService genreService;

    private Artist firstSavedArtist;
    private Artist secondSavedArtist;

    private TrackGenre firstSavedTrackGenre;
    private TrackGenre secondSavedTrackGenre;

    @BeforeEach
    void setUp() {
        Artist firstArtist = new Artist();
        firstSavedArtist = artistService.save(firstArtist);

        Artist secondArtist = new Artist();
        secondSavedArtist = artistService.save(secondArtist);

        TrackGenre firstTrackGenre = new TrackGenre();
        firstTrackGenre.setName("FIRST_TRACK_GENRE");
        firstSavedTrackGenre = genreService.save(firstTrackGenre);

        TrackGenre secondTrackGenre = new TrackGenre();
        secondTrackGenre.setName("SECOND_TRACK_GENRE");
        secondSavedTrackGenre = genreService.save(secondTrackGenre);
    }

    @AfterEach
    void tearDown() {
        singleRepository.deleteAll();
        trackGenreRepository.deleteAll();
    }

    @Test
    void save_ShouldThrowIllegalStateException_WhenSingleDoesNotHaveAnArtist(){
        Single single = new Single();
        assertThrows(IllegalStateException.class, () -> singleServiceImp.save(single));
    }

    @Test
    void save_ShouldSaveTheSingle_WhenSingleHaveAnArtist(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        Single savedSingle = singleServiceImp.save(single);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
    }

    @Test
    void save_ShouldSaveSingleWithDetails_WhenDetailsArePresent(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        single.setTitle("title");
        single.setDescription("description");
        single.setAddedDate(LocalDate.now());
        single.setDurationInSeconds(100);
        single.setFilePath("test://filepath");
        single.addGenre(firstSavedTrackGenre);
        Single savedSingle = singleServiceImp.save(single);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
        assertEquals(savedSingle, retrievedSingle);
    }

    @Test
    void save_ShouldUpdateSingleDetails_WhenSingleDetailsAreProvided(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        Single savedSingle = singleServiceImp.save(single);

        savedSingle.setTitle("title");
        savedSingle.setDescription("description");
        savedSingle.setAddedDate(LocalDate.now());
        savedSingle.setDurationInSeconds(100);
        savedSingle.setFilePath("test://filepath");
        savedSingle.addGenre(firstSavedTrackGenre);
        singleServiceImp.save(savedSingle);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
        assertEquals(savedSingle, retrievedSingle);
    }

    @Test
    void save_ShouldUpdateArtistSet_WhenAnotherArtistIsAdded(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        Single savedSingle = singleServiceImp.save(single);

        savedSingle.addArtist(secondSavedArtist);
        singleServiceImp.save(savedSingle);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
        assertEquals(2, retrievedSingle.getArtistSet().size());
    }

    @Test
    void save_ShouldUpdateArtistSet_WhenAnArtistIsRemoved(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        single.addArtist(secondSavedArtist);
        Single savedSingle = singleServiceImp.save(single);

        savedSingle.removeArtist(firstSavedArtist);
        singleServiceImp.save(savedSingle);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
        assertEquals(1, retrievedSingle.getArtistSet().size());
    }

    @Test
    void save_ShouldUpdateGenreSet_WhenAnotherGenreIsAdded(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        single.addGenre(firstSavedTrackGenre);
        Single savedSingle = singleServiceImp.save(single);

        savedSingle.addGenre(secondSavedTrackGenre);
        singleServiceImp.save(savedSingle);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
        assertEquals(2, retrievedSingle.getGenreSet().size());
    }

    @Test
    void save_ShouldUpdateGenreSet_WhenAGenreIsRemoved(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);
        single.addGenre(firstSavedTrackGenre);
        single.addGenre(secondSavedTrackGenre);
        Single savedSingle = singleServiceImp.save(single);

        savedSingle.removeGenre(secondSavedTrackGenre);
        singleServiceImp.save(savedSingle);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNotNull(retrievedSingle);
        assertEquals(1, retrievedSingle.getGenreSet().size());
    }

    @Test
    void delete_ShouldDeleteSingle(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);

        Single savedSingle = singleServiceImp.save(single);
        singleServiceImp.delete(savedSingle);

        Single retrievedSingle = singleServiceImp.findById(savedSingle.getId()).orElse(null);
        assertNull(retrievedSingle);
    }

    @Test
    void delete_ShouldNotDeleteArtist_WhenSingleIsDeleted(){
        Single single = new Single();
        single.addArtist(firstSavedArtist);

        Single savedSingle = singleServiceImp.save(single);
        singleServiceImp.delete(savedSingle);

        Artist retrievedArtist = artistService.findById(firstSavedArtist.getId()).orElse(null);
        assertNotNull(retrievedArtist);
    }
}