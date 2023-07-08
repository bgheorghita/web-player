package dev.gb.webplayerserver.services.concrete.collections.impl;

import dev.gb.webplayerserver.models.concrete.collections.Album;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import dev.gb.webplayerserver.services.concrete.collections.AlbumService;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AlbumServiceImp extends CrudServiceImp<Album, Long> implements AlbumService {
    public Album save(Album album) {
        validateAssociatedArtists(album);
        validateAssociatedSongs(album);
        return super.save(album);
    }

    private void validateAssociatedArtists(Album album) {
        Set<Artist> associatedArtists = album.getArtistSet();
        if(associatedArtists.isEmpty()){
            throw new IllegalStateException("Album must have at least one artist.");
        }
        associatedArtists.forEach(artist -> {
            if(artist.getId() == null){
                throw new IllegalStateException("Artists must be saved first.");
            }
        });
    }

    private void validateAssociatedSongs(Album album) {
        if(album.getSongSet().isEmpty()){
            throw new IllegalStateException("Album must have at least one song.");
        }
    }
}