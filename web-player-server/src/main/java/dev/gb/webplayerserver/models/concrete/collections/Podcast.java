package dev.gb.webplayerserver.models.concrete.collections;

import dev.gb.webplayerserver.models.base.AudioFileCollection;
import dev.gb.webplayerserver.models.concrete.audio.Episode;
import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import dev.gb.webplayerserver.models.concrete.genres.PodcastGenre;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PODCASTS")
public class Podcast extends AudioFileCollection {
    @ManyToMany(mappedBy = "podcastSet")
    private Set<Podcaster> hostSet = new HashSet<>();

    @OneToMany(mappedBy = "podcast")
    private Set<Episode> episodeSet = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "PODCAST_GENRES",
            joinColumns = @JoinColumn(name = "PODCAST_ID")
    )
    @Column(name = "GENRES")
    @Enumerated(EnumType.STRING)
    private Set<PodcastGenre> genreSet = new HashSet<>();

    private LocalDate creationDate;


    public Set<Podcaster> getHostSet() {
        return new HashSet<>(hostSet);
    }

    public void setHostSet(Set<Podcaster> hostSet) {
        this.hostSet = hostSet;
    }

    public Set<Episode> getEpisodeSet() {
        return new HashSet<>(episodeSet);
    }

    public void setEpisodeSet(Set<Episode> episodeSet) {
        this.episodeSet = episodeSet;
    }

    public Set<PodcastGenre> getGenreSet() {
        return new HashSet<>(genreSet);
    }

    public void setGenreSet(Set<PodcastGenre> genreSet) {
        this.genreSet = genreSet;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
