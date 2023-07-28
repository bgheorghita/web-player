package dev.gb.webplayerserver.services.audio;

import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.services.crud.CrudService;

import java.util.Set;

public interface SongService extends CrudService<Song, Long> {
    Set<Song> findByAlbumId(Long albumId);
}
