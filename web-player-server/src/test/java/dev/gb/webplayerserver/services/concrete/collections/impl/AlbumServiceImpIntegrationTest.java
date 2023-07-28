package dev.gb.webplayerserver.services.concrete.collections.impl;

import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.models.concrete.collections.Album;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import dev.gb.webplayerserver.repositories.collections.AlbumRepository;
import dev.gb.webplayerserver.repositories.creators.ArtistRepository;
import dev.gb.webplayerserver.repositories.genres.TrackGenreRepository;
import dev.gb.webplayerserver.services.audio.SongService;
import dev.gb.webplayerserver.services.collections.impl.AlbumServiceImp;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AlbumServiceImpIntegrationTest {
    @Autowired
    private AlbumServiceImp albumServiceImp;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GenreService genreService;
    @Autowired
    private TrackGenreRepository trackGenreRepository;
    @Autowired
    private SongService songService;

    private Artist firstSavedArtist;
    private Artist secondSavedArtist;
    private Artist thirdSavedArtist;

    private Song firstSong;
    private Song secondSong;
    private Song thirdSong;

    private TrackGenre trackGenreSaved;


    @BeforeEach
    void setUp() {
        Artist firstArtist = new Artist();
        firstSavedArtist = artistService.save(firstArtist);

        Artist secondArtist = new Artist();
        secondSavedArtist = artistService.save(secondArtist);

        Artist thirdArtist = new Artist();
        thirdSavedArtist = artistService.save(thirdArtist);

        firstSong = new Song();
        firstSong.setTitle("FIRST SONG TITLE");
        secondSong = new Song();
        secondSong.setTitle("SECOND SONG TITLE");
        thirdSong = new Song();
        thirdSong.setTitle("THIRD SECOND TITLE");

        TrackGenre trackGenre = new TrackGenre();
        trackGenre.setName("TRACK_GENRE");
        trackGenreSaved = genreService.save(trackGenre);
    }

    @AfterEach
    void tearDown() {
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        trackGenreRepository.deleteAll();
    }

    @Test
    void save_ShouldThrowIllegalStateException_WhenItDoesNotHaveAtLeastOneArtist(){
        Album album = new Album();
        assertThrows(IllegalStateException.class, () -> albumServiceImp.save(album));
    }

    @Test
    void save_ShouldThrowIllegalStateException_WhenItDoesNotHaveAtLeastOneSong(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        assertThrows(IllegalStateException.class, () -> albumServiceImp.save(album));
    }

    @Test
    void save_ShouldPersistAlbum_WhenItHasAtLeastOneArtistAndAtLeastOneSong(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);

        Song song = new Song();
        album.addSong(song);

        Album savedAlbum = albumServiceImp.save(album);
        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);

        assertNotNull(retrievedAlbum);
    }

    @Test
    void save_ShouldPersistAlbum_WhenItHasMultipleArtistsAndMultipleSongs(){
        Album album = new Album();
        album.addArtistSet(Set.of(firstSavedArtist, secondSavedArtist, thirdSavedArtist));
        album.addSongSet(Set.of(firstSong, secondSong, thirdSong));

        Album savedAlbum = albumServiceImp.save(album);
        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);

        assertNotNull(retrievedAlbum);
        assertEquals(3, album.getSongSet().size());
        assertEquals(3, album.getArtistSet().size());
    }

    @Test
    void save_ShouldUpdateAlbum_WhenNewSongsHasBeenAdded(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        album.addSong(firstSong);
        Album savedAlbum = albumServiceImp.save(album);

        savedAlbum.addSong(secondSong);
        savedAlbum = albumServiceImp.save(savedAlbum);

        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);

        assertNotNull(retrievedAlbum);
        assertEquals(2, retrievedAlbum.getSongSet().size());
    }

    @Test
    void save_ShouldUpdateAlbum_WhenNewArtistsHasBeenAdded(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        album.addSong(firstSong);
        Album savedAlbum = albumServiceImp.save(album);

        savedAlbum.addArtist(secondSavedArtist);
        savedAlbum = albumServiceImp.save(savedAlbum);

        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);

        assertNotNull(retrievedAlbum);
        assertEquals(2, retrievedAlbum.getArtistSet().size());
    }

    @Test
    void save_ShouldUpdateAlbum_WhenASongIsDeleted(){

    }

    @Test
    void save_ShouldUpdateSongDetailsWithinAlbum_WhenAlbumIsSaved(){
        // Create an album with an artist and a song
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        album.addSong(firstSong);

        // Save the album
        Album savedAlbum = albumServiceImp.save(album);
        assertNotNull(savedAlbum);

        // Retrieve the saved album
        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);
        assertNotNull(retrievedAlbum);

        // Get the first song from the retrieved album
        Song song = retrievedAlbum.getSongSet().stream().findFirst().orElse(null);
        assertNotNull(song);

        // Update the song details
        song.setTitle("SONG");
        song.setLyrics("LYRICS");
        song.setDescription("DESCRIPTION");
        song.setAddedDate(LocalDate.now());
        song.setDurationInSeconds(100);
        song.setFilePath("FILEPATH");
        song.addGenre(trackGenreSaved);

        // Save the album again to persist the updated song details
        savedAlbum = albumServiceImp.save(retrievedAlbum);
        assertNotNull(savedAlbum);

        // Retrieve the album once more to ensure the updates were persisted
        retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);
        assertNotNull(retrievedAlbum);

        // Get the updated song from the retrieved album
        Song updatedSong = retrievedAlbum.getSongSet().stream().findFirst().orElse(null);
        assertNotNull(updatedSong);

        // Compare the expected song details with the updated song details
        assertEquals(song.getId(), updatedSong.getId());
    }

    @Test
    void delete_ShouldDeleteAllSongsInAlbum_WhenAlbumIsDeleted(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        album.addSong(firstSong);
        album.addSong(secondSong);
        album.addSong(thirdSong);

        Album savedAlbum = albumServiceImp.save(album);
        Set<Song> songsInAlbumBeforeDelete = songService.findByAlbumId(savedAlbum.getId());
        assertEquals(3, songsInAlbumBeforeDelete.size());

        albumServiceImp.delete(savedAlbum);
        Set<Song> songsInAlbumAfterDelete = songService.findByAlbumId(savedAlbum.getId());
        assertEquals(0, songsInAlbumAfterDelete.size());
    }

    @Test
    void save_ShouldUpdateAlbumDetails_WhenAreSet(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        album.addSong(firstSong);

        Album savedAlbum = albumServiceImp.save(album);
        savedAlbum.setTitle("TITLE");
        savedAlbum.setReleaseDate(LocalDate.now());
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("ALBUM");
        customDesign.setCoverImagePath("COV_IMG_PATH");
        customDesign.setProfileImagePath("PROF_IMG_PATH");
        savedAlbum.setCustomDesign(customDesign);

        savedAlbum = albumServiceImp.save(savedAlbum);
        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);
        assertNotNull(retrievedAlbum);
        assertEquals(savedAlbum, retrievedAlbum);
    }

    @Test
    @Transactional
    void save_ShouldUpdateAlbum_WhenNewSongIsAddedToIt(){
        Album album = new Album();
        album.addArtist(firstSavedArtist);
        album.addSong(firstSong);

        Album savedAlbum = albumServiceImp.save(album);
        Album retrievedAlbum = albumServiceImp.findById(savedAlbum.getId()).orElse(null);
        assertNotNull(retrievedAlbum);
        int songSetSizeBeforeSavingNewSong = retrievedAlbum.getSongSet().size();

        Song newSong = new Song();
        newSong.setAlbum(album);
        songService.save(newSong);

        int songSetSizeAfterNewSongSaved = retrievedAlbum.getSongSet().size();
        assertEquals(songSetSizeBeforeSavingNewSong + 1, songSetSizeAfterNewSongSaved);
    }
}