package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.concrete.collections.Album;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "SONGS")
public class Song extends Track {
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ALBUM_ID", nullable = false)
    private Album album;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
        album.addSong(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        return Objects.equals(this.getTitle(), song.getTitle()) && Objects.equals(this.getId(), song.getId());
    }

    @Override
    public int hashCode() {
        int hash = 1;
        if(getId() != null){
            hash = 31 * hash * getId().hashCode();
        }
        if(getTitle() != null){
            hash = 31 * hash * getTitle().hashCode();
        }
        return hash;
    }
}
