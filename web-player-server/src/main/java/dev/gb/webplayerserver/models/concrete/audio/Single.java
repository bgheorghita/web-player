package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.base.AudioFile;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.details.TrackDetails;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SINGLES")
public class Single extends AudioFile {
    @ManyToMany(mappedBy = "singleSet")
    private Set<Artist> artistSet = new HashSet<>();

    @Embedded
    private TrackDetails trackDetails;


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
