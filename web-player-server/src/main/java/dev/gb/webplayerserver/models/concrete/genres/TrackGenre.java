package dev.gb.webplayerserver.models.concrete.genres;

import dev.gb.webplayerserver.models.concrete.audio.Track;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRACK_GENRES")
public class TrackGenre extends Genre {
    @ManyToMany(mappedBy = "genreSet")
    private final Set<Track> trackSet = new HashSet<>();

    public void addTrack(Track track){
        trackSet.add(track);
    }
}
