package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.base.AudioFile;
import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Track extends AudioFile {
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "TRACK_GENRE",
            joinColumns = @JoinColumn(name = "TRACK_ID"),
            inverseJoinColumns = @JoinColumn(name = "TRACK_GENRE_ID")
    )
    private final Set<TrackGenre> genreSet = new HashSet<>();
    private String lyrics;

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void removeGenre(TrackGenre trackGenre){
        genreSet.remove(trackGenre);
    }

    public Set<TrackGenre> getGenreSet() {
        return new HashSet<>(genreSet);
    }

    public void addGenres(Set<TrackGenre> trackGenreSet){
        trackGenreSet.forEach(trackGenre -> {
            genreSet.add(trackGenre);
            trackGenre.addTrack(this);
        });
    }

    public void addGenre(TrackGenre trackGenre){
        addGenres(Set.of(trackGenre));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        if (!super.equals(o)) return false;

        Track track = (Track) o;

        if (!genreSet.equals(track.genreSet)) return false;
        return Objects.equals(lyrics, track.lyrics);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + genreSet.hashCode();
        result = 31 * result + (lyrics != null ? lyrics.hashCode() : 0);
        return result;
    }
}
