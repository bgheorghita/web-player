package dev.gb.webplayerserver.models.concrete.creators;

import dev.gb.webplayerserver.models.base.Creator;
import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PODCASTERS")
public class Podcaster extends Creator {
    @ManyToMany
    @JoinTable(
            name = "PODCASTER_PODCAST",
            joinColumns = @JoinColumn(name = "PODCASTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PODCAST_ID")
    )
    private Set<Podcast> podcastSet = new HashSet<>();

    public Set<Podcast> getPodcastSet() {
        return new HashSet<>(podcastSet);
    }

    public void setPodcastSet(Set<Podcast> podcastSet) {
        this.podcastSet = podcastSet;
    }
}
