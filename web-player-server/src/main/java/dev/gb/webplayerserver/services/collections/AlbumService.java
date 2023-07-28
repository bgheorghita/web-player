package dev.gb.webplayerserver.services.collections;

import dev.gb.webplayerserver.models.concrete.collections.Album;
import dev.gb.webplayerserver.services.crud.CrudService;

public interface AlbumService extends CrudService<Album, Long> {
    Album save(Album album);
}
