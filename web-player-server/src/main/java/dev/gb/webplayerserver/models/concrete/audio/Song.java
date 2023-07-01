package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.base.AudioFile;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.details.TrackDetails;
import dev.gb.webplayerserver.models.concrete.collections.Album;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SONGS")
public class Song extends AudioFile {
    @ManyToOne
    private Album album;

    @ManyToMany(mappedBy = "songSet")
    private Set<Artist> artistSet = new HashSet<>();

    @Embedded
    private TrackDetails trackDetails;


    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Artist> getArtistSet() {
        return new HashSet<>(artistSet);
    }

    public void setArtistSet(Set<Artist> artistSet) {
        this.artistSet = artistSet;
    }

    public TrackDetails getTrackDetails() {
        return trackDetails;
    }

    public void setTrackDetails(TrackDetails trackDetails) {
        this.trackDetails = trackDetails;
    }
}
