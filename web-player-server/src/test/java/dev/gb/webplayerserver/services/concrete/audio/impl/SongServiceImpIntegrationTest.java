package dev.gb.webplayerserver.services.concrete.audio.impl;

import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.models.concrete.collections.Album;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.repositories.audio.SongRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class SongServiceImpIntegrationTest {
    @Autowired
    private SongServiceImp songServiceImp;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongRepository songRepository;

    private Album savedAlbum;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        Artist savedArtist = artistService.save(artist);

        Album album = new Album();
        album.addArtist(savedArtist);

        Song firstSong = new Song();
        firstSong.setTitle("first_song");
        Song secondSong = new Song();
        secondSong.setTitle("second_song");

        album.addSong(firstSong);
        album.addSong(secondSong);

        savedAlbum = albumService.save(album);
    }

    @AfterEach
    void tearDown() {
        songRepository.deleteAll();
    }

    @Test
    void save_ShouldThrowAnException_WhenSongIsNotPartOfAnAlbum(){
        Song song = new Song();
        assertThrows(IllegalStateException.class, () -> songServiceImp.save(song));
    }

    @Test
    void findByAlbumId_ShouldRetrieveSongSet_WhenThoseSongsBelongToAlbum(){
        long albumId = savedAlbum.getId();
        Set<Song> songSet = songServiceImp.findByAlbumId(albumId);
        int numberOfSongsInAlbum = savedAlbum.getSongSet().size();
        int numberOfSongsRetrieved = songSet.size();
        assertEquals(numberOfSongsInAlbum, numberOfSongsRetrieved);
    }

    @Test
    void delete_ShouldDeleteSongFromAlbum_WhenExists(){
        Set<Song> songSetBeforeDelete = songServiceImp.findByAlbumId(savedAlbum.getId());

        System.out.println(songSetBeforeDelete.size());
        Song songInAlbum = songSetBeforeDelete.stream().iterator().next();
        songServiceImp.delete(songInAlbum);

        Set<Song> songSetAfterDelete = songServiceImp.findByAlbumId(savedAlbum.getId());
        assertEquals(songSetBeforeDelete.size() - 1, songSetAfterDelete.size());
    }

    @Test
    void save_ShouldPersistSong_WhenItIsAddedToAnExistentAlbum(){
        Song newSong = new Song();
        newSong.setTitle("NEW SONG");
        newSong.setAlbum(savedAlbum);

        Set<Song> songSetBeforeSavingNewSong = songServiceImp.findByAlbumId(savedAlbum.getId());
        songRepository.save(newSong);
        Set<Song> songSetAfterSavingNewSong = songServiceImp.findByAlbumId(savedAlbum.getId());

        assertEquals(songSetBeforeSavingNewSong.size() + 1, songSetAfterSavingNewSong.size());
    }

    @Test
    void save_ShouldThrowIllegalStateExceptionException_WhenSongHasUnsavedAlbum(){
        Song song = new Song();
        Album newAlbum = new Album();
        song.setAlbum(newAlbum);

        assertThrows(IllegalStateException.class, () -> songServiceImp.save(song));
    }

    @Test
    void delete_ShouldNotDeleteAlbum_WhenSongIsDeleted_IfThereAreSongsLeft(){
        Song firstNewSong = new Song();
        firstNewSong.setTitle("FIRST NEW SONG");
        Song secondNewSong = new Song();
        secondNewSong.setTitle("SECOND NEW SONG");
        savedAlbum.addSongSet(Set.of(firstNewSong, secondNewSong));

        savedAlbum = albumService.save(savedAlbum);
        Set<Song> songsInAlbum = songServiceImp.findByAlbumId(savedAlbum.getId());

        Song songInAlbum = songsInAlbum.stream().iterator().next();
        songServiceImp.delete(songInAlbum);

        Album retrievedAlbum = albumService.findById(savedAlbum.getId()).orElse(null);
        assertNotNull(retrievedAlbum);
    }

    @Test
    @Transactional
    void delete_ShouldDeleteAlbum_WhenSongIsDeleted_IfAlbumRemainsEmpty(){
        Song firstNewSong = new Song();
        firstNewSong.setTitle("FIRST NEW SONG");
        Song secondNewSong = new Song();
        secondNewSong.setTitle("SECOND NEW SONG");
        savedAlbum.addSongSet(Set.of(firstNewSong, secondNewSong));
        savedAlbum = albumService.save(savedAlbum);

        Set<Song> songsInAlbum = songServiceImp.findByAlbumId(savedAlbum.getId());
        songsInAlbum.forEach(song -> songServiceImp.delete(song));

        Album retrievedAlbum = albumService.findById(savedAlbum.getId()).orElse(null);
        assertNull(retrievedAlbum);
    }
}