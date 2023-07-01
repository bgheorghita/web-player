package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.base.AudioFile;
import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EPISODES")
public class Episode extends AudioFile {
    @ManyToOne
    private Podcast podcast;
    private Set<String> guestSet = new HashSet<>();

    public Podcast getPodcast() {
        return podcast;
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
    }

    public Set<String> getGuestSet() {
        return new HashSet<>(guestSet);
    }

    public void setGuestSet(Set<String> guestSet) {
        this.guestSet = guestSet;
    }
}
