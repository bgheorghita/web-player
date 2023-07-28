package dev.gb.webplayerserver.models.concrete.details;

import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Embeddable
public class TrackDetails {
    private String lyrics;

    @ManyToMany
    private Set<TrackGenre> genreSet = new HashSet<>();

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Set<TrackGenre> getGenreSet() {
        return new HashSet<>(genreSet);
    }

    public void setGenreSet(Set<TrackGenre> genreSet) {
        this.genreSet = genreSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackDetails that = (TrackDetails) o;
        return Objects.equals(lyrics, that.lyrics) && Objects.equals(genreSet, that.genreSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lyrics, genreSet);
    }
}
