package dev.gb.webplayerserver.models.concrete.creators;

import dev.gb.webplayerserver.models.base.Creator;
import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PODCASTERS")
public class Podcaster extends Creator {
    @ManyToMany(mappedBy = "podcasterSet", fetch = FetchType.EAGER)
    private Set<Podcast> podcastSet = new HashSet<>();

    public void addPodcast(Podcast podcast){
        podcastSet.add(podcast);
        podcast.addPodcaster(this);
    }

    public Set<Podcast> getPodcastSet() {
        return new HashSet<>(podcastSet);
    }

    public void setPodcastSet(Set<Podcast> podcastSet) {
        this.podcastSet = podcastSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Podcaster)) return false;
        if (!super.equals(o)) return false;

        Podcaster podcaster = (Podcaster) o;

        return Objects.equals(podcastSet, podcaster.podcastSet);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (podcastSet != null ? podcastSet.hashCode() : 0);
        return result;
    }
}
