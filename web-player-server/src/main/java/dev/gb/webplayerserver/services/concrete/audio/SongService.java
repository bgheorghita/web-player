package dev.gb.webplayerserver.services.concrete.audio;

import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.services.base.crud.CrudService;

import java.util.Set;

public interface SongService extends CrudService<Song, Long> {
    Set<Song> findByAlbumId(Long albumId);
}
