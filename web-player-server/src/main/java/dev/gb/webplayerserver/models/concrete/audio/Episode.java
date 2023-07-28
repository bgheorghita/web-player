package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.base.AudioFile;
import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "EPISODES")
public class Episode extends AudioFile {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Episode)) return false;
        if (!super.equals(o)) return false;

        Episode episode = (Episode) o;

        if (!Objects.equals(podcast, episode.podcast)) return false;
        return Objects.equals(guestSet, episode.guestSet);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (podcast != null ? podcast.hashCode() : 0);
        result = 31 * result + (guestSet != null ? guestSet.hashCode() : 0);
        return result;
    }
}
