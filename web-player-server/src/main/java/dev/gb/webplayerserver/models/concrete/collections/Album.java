package dev.gb.webplayerserver.models.concrete.collections;

import dev.gb.webplayerserver.models.base.AudioFileCollection;
import dev.gb.webplayerserver.models.concrete.audio.Song;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ALBUMS")
public class Album extends AudioFileCollection {
    @OneToMany(
            mappedBy = "album",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private final Set<Song> songSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ALBUM_ARTIST",
            joinColumns = @JoinColumn(name = "ALBUM_ID"),
            inverseJoinColumns = @JoinColumn(name = "ARTIST_ID")
    )
    private final Set<Artist> artistSet = new HashSet<>();

    private LocalDate releaseDate;

    public void addArtistSet(Set<Artist> artistSet){
        artistSet.forEach(this::addArtist);
    }

    public void addArtist(Artist artist){
        artistSet.add(artist);
        addAlbumToArtist(artist);
    }

    private void addAlbumToArtist(Artist artist) {
        boolean artistDoesNotAppearOnThisAlbum = !artist.hasAlbum(this);
        if(artistDoesNotAppearOnThisAlbum){
            artist.addAlbum(this);
        }
    }

    public Set<Artist> getArtistSet() {
        return new HashSet<>(artistSet);
    }

    public Set<Song> getSongSet() {
        return new HashSet<>(songSet);
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void addSongSet(Set<Song> songSet) {
        songSet.forEach(this::addSong);
    }

    public void addSong(Song song){
        songSet.add(song);
        setThisAlbumToSong(song);
    }

    private void setThisAlbumToSong(Song song) {
        if(song.getAlbum() != this){
            song.setAlbum(this);
        }
    }

    public void removeSong(Song song){
        songSet.remove(song);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        if (!super.equals(o)) return false;

        Album album = (Album) o;

        if (!songSet.equals(album.songSet)) return false;
        if (!artistSet.equals(album.artistSet)) return false;
        return Objects.equals(releaseDate, album.releaseDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + songSet.hashCode();
        result = 31 * result + artistSet.hashCode();
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        return result;
    }
}
