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
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "PODCASTER_PODCAST",
            joinColumns = @JoinColumn(name = "PODCAST_ID"),
            inverseJoinColumns = @JoinColumn(name = "PODCASTER_ID")
    )
    private Set<Podcaster> podcasterSet = new HashSet<>();

    @OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Episode> episodeSet = new HashSet<>();

   @ManyToMany(cascade = CascadeType.MERGE)
   @JoinTable(
           name = "PODCAST_GENRE",
           joinColumns = @JoinColumn(name = "PODCAST_ID"),
           inverseJoinColumns = @JoinColumn(name = "PODCAST_GENRE_ID")
   )
    private Set<PodcastGenre> genreSet = new HashSet<>();

    private LocalDate creationDate;

    public void addPodcaster(Podcaster podcaster){
        podcasterSet.add(podcaster);
    }

    public void addEpisode(Episode episode){
        episodeSet.add(episode);
        episode.setPodcast(this);
    }

    public Set<Podcaster> getPodcasterSet() {
        return new HashSet<>(podcasterSet);
    }

    public void setPodcasterSet(Set<Podcaster> podcasterSet) {
        this.podcasterSet = podcasterSet;
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
