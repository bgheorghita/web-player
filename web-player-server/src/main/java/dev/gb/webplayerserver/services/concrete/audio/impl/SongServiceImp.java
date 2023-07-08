package dev.gb.webplayerserver.services.concrete.audio.impl;

import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.repositories.audio.SongRepository;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import dev.gb.webplayerserver.services.concrete.audio.SongService;
import dev.gb.webplayerserver.services.concrete.collections.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SongServiceImp extends CrudServiceImp<Song, Long> implements SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumService albumService;

    @Override
    public Song save(Song song){
        validateAssociatedAlbum(song);
        return super.save(song);
    }

    private void validateAssociatedAlbum(Song song) {
        if(song.getAlbum() == null){
            throw new IllegalStateException("Song must be part of an album.");
        }

        Long albumId = song.getAlbum().getId();
        if(albumId == null){
            throw new IllegalStateException("The album must be saved into database before adding new songs.");
        }
    }

    @Override
    public Set<Song> findByAlbumId(Long albumId) {
        return songRepository.findByAlbumId(albumId);
    }

    @Override
    public void delete(Song song){
        removeAlbumRef(song);
        deleteAlbumIfEmpty(song);
        super.delete(song);
    }

    private void deleteAlbumIfEmpty(Song song) {
        Set<Song> songsInAlbum = song.getAlbum().getSongSet();
        if(songsInAlbum.isEmpty()){
            albumService.delete(song.getAlbum());
        }
    }

    private void removeAlbumRef(Song song){
        song.getAlbum().removeSong(song);
    }
}
