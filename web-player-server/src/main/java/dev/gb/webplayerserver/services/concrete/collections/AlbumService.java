package dev.gb.webplayerserver.services.concrete.collections;

import dev.gb.webplayerserver.models.concrete.collections.Album;
import dev.gb.webplayerserver.services.base.crud.CrudService;

public interface AlbumService extends CrudService<Album, Long> {
    Album save(Album album);
}
