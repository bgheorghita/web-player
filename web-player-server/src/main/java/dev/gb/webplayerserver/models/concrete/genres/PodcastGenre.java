package dev.gb.webplayerserver.models.concrete.genres;

import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PODCAST_GENRES")
public class PodcastGenre extends Genre {
    @ManyToMany(mappedBy = "genreSet")
    private final Set<Podcast> podcastSet = new HashSet<>();

    public Set<Podcast> getPodcastSet() {
        return new HashSet<>(podcastSet);
    }

    public void addPodcast(Podcast podcast){
        podcastSet.add(podcast);
    }
}
