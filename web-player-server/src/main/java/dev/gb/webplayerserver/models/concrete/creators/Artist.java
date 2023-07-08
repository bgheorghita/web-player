package dev.gb.webplayerserver.models.concrete.creators;

import dev.gb.webplayerserver.models.base.Creator;
import dev.gb.webplayerserver.models.concrete.audio.Single;
import dev.gb.webplayerserver.models.concrete.collections.Album;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ARTISTS")
public class Artist extends Creator {
    @ManyToMany(mappedBy = "artistSet", fetch = FetchType.EAGER)
    private final Set<Album> albumSet = new HashSet<>();

    @ManyToMany(mappedBy = "artistSet", fetch = FetchType.EAGER)
    private final Set<Single> singleSet = new HashSet<>();


    public void addAlbum(Album album){
        albumSet.add(album);
        album.addArtist(this);
    }

    public boolean hasAlbum(Album album){
        return albumSet.contains(album);
    }

    public Set<Single> getSingleSet() {
        return new HashSet<>(singleSet);
    }

    public Set<Album> getAlbumSet() {
        return new HashSet<>(albumSet);
    }
}
