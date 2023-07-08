package dev.gb.webplayerserver.services.concrete.creators.impl;

import dev.gb.webplayerserver.models.concrete.audio.Single;
import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.models.concrete.collections.Album;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.repositories.collections.AlbumRepository;
import dev.gb.webplayerserver.repositories.creators.ArtistRepository;
import dev.gb.webplayerserver.repositories.audio.SingleRepository;
import dev.gb.webplayerserver.services.concrete.audio.SingleService;
import dev.gb.webplayerserver.services.concrete.collections.AlbumService;
import dev.gb.webplayerserver.services.concrete.creators.ArtistService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ArtistServiceImpIntegrationTest {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private SingleRepository singleRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private SingleService singleService;
    @Autowired
    private AlbumService albumService;

    private Artist savedArtist;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        savedArtist = artistService.save(artist);
    }

    @AfterEach
    void tearDown() {
        singleRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    void save_ShouldPersistArtist(){
        Artist newArtist = new Artist();
        Artist savedNewArtist = artistService.save(newArtist);

        Artist retrevedArtist = artistService.findById(savedNewArtist.getId()).orElse(null);
        assertEquals(savedNewArtist, retrevedArtist);
    }

    @Test
    void save_ShouldUpdateArtist_WhenCustomDesignIsAdded(){
        savedArtist.setJoinedDate(LocalDate.now());
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("ARTIST");
        customDesign.setCoverImagePath("COV_IMG");
        customDesign.setProfileImagePath("PRF_IMG");
        savedArtist.setCustomDesign(customDesign);

        savedArtist = artistService.save(savedArtist);
        Artist retrevedArtist = artistService.findById(savedArtist.getId()).orElse(null);
        assertEquals(savedArtist, retrevedArtist);
    }

    @Test
    void save_ShouldUpdateArtist_WhenSingleIsAdded(){
        Single single = new Single();
        single.addArtist(savedArtist);
        Single savedSingle = singleService.save(single);

        Artist retrevedArtist = artistService.findById(savedArtist.getId()).orElse(null);

        assertNotNull(retrevedArtist);
        assertEquals(1, retrevedArtist.getSingleSet().size());
        assertEquals(savedSingle, retrevedArtist.getSingleSet().iterator().next());
    }

    @Test
    void save_ShouldUpdateArtist_WhenAlbumIsAdded(){
        Song song = new Song();
        Album album = new Album();
        album.addSong(song);

        savedArtist.addAlbum(album);
        savedArtist = artistService.save(savedArtist);
        Artist retrevedArtist = artistService.findById(savedArtist.getId()).orElse(null);

        assertEquals(savedArtist, retrevedArtist);
    }

    @Test
    void findById_ShouldRetrieveArtistWithAssociatedSingles_WhenSinglesArePresent(){
        Single firstSingle = new Single();
        Single secondSingle = new Single();
        firstSingle.addArtist(savedArtist);
        secondSingle.addArtist(savedArtist);

        singleService.save(firstSingle);
        singleService.save(secondSingle);

        Artist retrievedArtist = artistService.findById(savedArtist.getId()).orElse(null);
        assertNotNull(retrievedArtist);
        Set<Single> artistSingles = retrievedArtist.getSingleSet();

        assertEquals(2, retrievedArtist.getSingleSet().size());
        assertTrue(artistSingles.contains(firstSingle));
        assertTrue(artistSingles.contains(secondSingle));
    }

    @Test
    void findById_ShouldRetrieveArtistWithAssociatedAlbum_WhenAlbumIsPresent(){
        Album album = new Album();
        Song song = new Song();
        song.setTitle("SONG");
        song.setAlbum(album);
        album.addArtist(savedArtist);

        albumService.save(album);
        Artist retrievedArtist = artistService.findById(savedArtist.getId()).orElse(null);
        assertNotNull(retrievedArtist);

        Set<Song> songsInAlbum = retrievedArtist.getAlbumSet().stream().iterator().next().getSongSet();
        assertEquals(1, retrievedArtist.getAlbumSet().size());
        assertEquals(1, songsInAlbum.size());
        assertTrue(songsInAlbum.contains(song));
    }

    @Test
    void delete_ShouldUpdateArtist_WhenAssociatedSingleIsRemoved(){
        Single firstSingle = new Single();
        Single secondSingle = new Single();
        firstSingle.addArtist(savedArtist);
        secondSingle.addArtist(savedArtist);
        Single firstSingleSaved = singleService.save(firstSingle);
        Single secondSingleSaved = singleService.save(secondSingle);

        singleService.delete(firstSingleSaved);

        Artist retrievedArtist = artistService.findById(savedArtist.getId()).orElse(null);

        assertNotNull(retrievedArtist);
        assertEquals(1, retrievedArtist.getSingleSet().size());
        assertEquals(secondSingleSaved, retrievedArtist.getSingleSet().stream().iterator().next());
    }

    @Test
    void delete_ShouldUpdateArtist_WhenAssociatedAlbumIsRemoved(){
        Song song = new Song();

        Album album = new Album();
        album.addSong(song);
        album.addArtist(savedArtist);

        Album savedAlbum = albumService.save(album);
        albumService.delete(savedAlbum);
        Artist retrievedArtist = artistService.findById(savedArtist.getId()).orElse(null);

        assertNotNull(retrievedArtist);
        assertEquals(0, retrievedArtist.getAlbumSet().size());
    }
}