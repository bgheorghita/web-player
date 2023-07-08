package dev.gb.webplayerserver.repositories.audio;

import dev.gb.webplayerserver.models.concrete.audio.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT song FROM Song song JOIN song.album album WHERE album.id = :albumId")
    Set<Song> findByAlbumId(@Param("albumId") Long albumId);
}
