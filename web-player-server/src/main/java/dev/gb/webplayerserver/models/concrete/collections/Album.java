package dev.gb.webplayerserver.models.concrete.collections;

import dev.gb.webplayerserver.models.base.AudioFileCollection;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.audio.Song;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ALBUMS")
public class Album extends AudioFileCollection {
    @OneToMany(mappedBy = "album")
    private Set<Song> songSet = new HashSet<>();

    @ManyToMany(mappedBy = "albumSet")
    private Set<Artist> artistSet = new HashSet<>();

    private LocalDate releaseDate;

    public Set<Song> getSongSet() {
        return new HashSet<>(songSet);
    }

    public void setSongSet(Set<Song> songSet) {
        this.songSet = songSet;
    }

    public Set<Artist> getArtistSet() {
        return new HashSet<>(artistSet);
    }

    public void setArtistSet(Set<Artist> artistSet) {
        this.artistSet = artistSet;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
