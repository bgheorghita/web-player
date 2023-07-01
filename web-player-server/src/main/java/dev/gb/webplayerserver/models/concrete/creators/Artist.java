package dev.gb.webplayerserver.models.concrete.creators;

import dev.gb.webplayerserver.models.base.Creator;
import dev.gb.webplayerserver.models.concrete.audio.Single;
import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.models.concrete.collections.Album;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ARTISTS")
public class Artist extends Creator {
    @ManyToMany
    @JoinTable(
            name = "ARTIST_SONG",
            joinColumns = @JoinColumn(name = "ARTIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "SONG_ID")
    )
    private Set<Song> songSet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "ARTIST_ALBUM",
            joinColumns = @JoinColumn(name = "ARTIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "ALBUM_ID")
    )
    private Set<Album> albumSet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "ARTIST_SINGLE",
            joinColumns = @JoinColumn(name = "ARTIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "SINGLE_ID")
    )
    private Set<Single> singleSet = new HashSet<>();


    public Set<Song> getSongSet() {
        return new HashSet<>(songSet);
    }

    public void setSongSet(Set<Song> songSet) {
        this.songSet = songSet;
    }

    public Set<Album> getAlbumSet() {
        return new HashSet<>(albumSet);
    }

    public void setAlbumSet(Set<Album> albumSet) {
        this.albumSet = albumSet;
    }

    public Set<Single> getSingleSet() {
        return new HashSet<>(singleSet);
    }

    public void setSingleSet(Set<Single> singleSet) {
        this.singleSet = singleSet;
    }
}
