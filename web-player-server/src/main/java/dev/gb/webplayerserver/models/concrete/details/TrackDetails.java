package dev.gb.webplayerserver.models.concrete.details;

import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Embeddable
public class TrackDetails {
    private String lyrics;

    @ElementCollection
    @CollectionTable(
            name = "TRACK_GENRES",
            joinColumns = @JoinColumn(name = "TRACK_ID")
    )
    @Column(name = "GENRES")
    @Enumerated(EnumType.STRING)
    private Set<TrackGenre> genreSet = new HashSet<>();

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Set<TrackGenre> getGenreSet() {
        return genreSet;
    }

    public void setGenreSet(Set<TrackGenre> genreSet) {
        this.genreSet = genreSet;
    }
}
