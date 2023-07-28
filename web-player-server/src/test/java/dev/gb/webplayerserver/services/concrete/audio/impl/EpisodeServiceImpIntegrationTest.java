package dev.gb.webplayerserver.services.concrete.audio.impl;

import dev.gb.webplayerserver.models.concrete.audio.Episode;
import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import dev.gb.webplayerserver.repositories.collections.PodcastRepository;
import dev.gb.webplayerserver.repositories.creators.PodcasterRepository;
import dev.gb.webplayerserver.services.audio.impl.EpisodeServiceImp;
import dev.gb.webplayerserver.services.collections.PodcastService;
import dev.gb.webplayerserver.services.creators.PodcasterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class EpisodeServiceImpIntegrationTest {
    @Autowired
    private EpisodeServiceImp episodeServiceImp;
    @Autowired
    private PodcastService podcastService;
    @Autowired
    private PodcasterService podcasterService;

    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private PodcasterRepository podcasterRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        podcastRepository.deleteAll();
        podcasterRepository.deleteAll();
    }

    @Test
    void save_ShouldThrowIllegalStateException_WhenEpisodeIsNotPartOfAPodcast(){
        Episode episode = new Episode();
        assertThrows(IllegalStateException.class, () -> episodeServiceImp.save(episode));
    }

    @Test
    void save_ShouldThrowIllegalStateException_WhenPodcastsIsNotSavedFirst(){
        Podcast podcast = new Podcast();
        Episode episode = new Episode();
        episode.setPodcast(podcast);
        assertThrows(IllegalStateException.class, () -> episodeServiceImp.save(episode));
    }

    @Test
    void save_ShouldPersistEpisode_WhenItHasSetAnExistentPodcast(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);
        podcastService.save(podcast);

        Episode episode = new Episode();
        episode.setPodcast(podcast);

        episodeServiceImp.save(episode);
        Episode retrievedEpisode = episodeServiceImp.findById(episode.getId()).orElse(null);

        assertNotNull(retrievedEpisode);
    }
}