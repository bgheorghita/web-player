package dev.gb.webplayerserver.services.concrete.collections.impl;

import dev.gb.webplayerserver.models.concrete.audio.Episode;
import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.models.concrete.genres.PodcastGenre;
import dev.gb.webplayerserver.repositories.genres.PodcastGenreRepository;
import dev.gb.webplayerserver.repositories.collections.PodcastRepository;
import dev.gb.webplayerserver.services.concrete.audio.EpisodeService;
import dev.gb.webplayerserver.services.concrete.creators.PodcasterService;
import dev.gb.webplayerserver.services.concrete.genres.PodcastGenreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PodcastServiceImpIntegrationTest {
    @Autowired
    private PodcastServiceImp podcastServiceImp;
    @Autowired
    private PodcasterService podcasterService;
    @Autowired
    private PodcastGenreService podcastGenreService;
    @Autowired
    private EpisodeService episodeService;
    @Autowired
    private PodcastGenreRepository podcastGenreRepository;
    @Autowired
    private PodcastRepository podcastRepository;


    private PodcastGenre podcastGenre;

    @BeforeEach
    void setUp() {
        podcastGenre = new PodcastGenre();
        podcastGenre.setName("PODCAST GENRE");
        podcastGenreService.save(podcastGenre);
    }

    @AfterEach
    void tearDown() {
        podcastRepository.deleteAll();
        podcastGenreRepository.deleteAll();
    }

    @Test
    void save_ShouldThrowIllegalStateException_WhenPodcastDoesNotHaveAtLeastOneHost(){
        Podcast podcast = new Podcast();
        assertThrows(IllegalStateException.class, () -> podcastServiceImp.save(podcast));
    }

    @Test
    void save_ShouldPersist_WhenPodcastHasAtLeastOnePodcaster(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);
        podcastServiceImp.save(podcast);

        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);

        assertNotNull(retrievedPodcast);
        assertEquals(podcast, retrievedPodcast);
    }

    @Test
    void save_ShouldPersistEpisode_WhenAddedToPodcast(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);

        Episode episode = new Episode();
        podcast.addEpisode(episode);

        podcastServiceImp.save(podcast);
        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);

        assertNotNull(retrievedPodcast);
        assertEquals(podcast, retrievedPodcast);
        assertEquals(episode, retrievedPodcast.getEpisodeSet().iterator().next());
    }

    @Test
    void save_ShouldUpdateEpisodeDetails_WhenTheyAreAdded(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);

        Episode episode = new Episode();
        podcast.addEpisode(episode);

        podcastServiceImp.save(podcast);

        episode.setAddedDate(LocalDate.now());
        episode.setGuestSet(Set.of("A", "B", "C"));
        episode.setDescription("DESC");
        episode.setTitle("TITLE");
        episode.setFilePath("PATH");
        episode.setDurationInSeconds(10000);

        podcastServiceImp.save(podcast);
        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);

        assertNotNull(retrievedPodcast);
        assertEquals(podcast, retrievedPodcast);
        assertEquals(episode, retrievedPodcast.getEpisodeSet().iterator().next());
    }

    @Test
    void save_ShouldBeAbleToPersistMultipleEpisodes(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);

        Episode firstEpisode = new Episode();
        firstEpisode.setTitle("EP1");
        Episode secondEpisode = new Episode();
        secondEpisode.setTitle("EP2");
        Episode thirdEpisode = new Episode();
        thirdEpisode.setTitle("EP3");
        podcast.addEpisode(firstEpisode);
        podcast.addEpisode(secondEpisode);
        podcast.addEpisode(thirdEpisode);

        podcastServiceImp.save(podcast);
        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);

        assertNotNull(retrievedPodcast);
        assertEquals(podcast, retrievedPodcast);
        assertEquals(3, retrievedPodcast.getEpisodeSet().size());
    }

    @Test
    void save_ShouldUpdatePodcasterSet_WhenNewPodcasterIsAddedToPodcast(){
        Podcaster firstPodcaster = new Podcaster();
        Podcaster secondPodcaster = new Podcaster();
        podcasterService.save(firstPodcaster);
        podcasterService.save(secondPodcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(firstPodcaster);
        podcastServiceImp.save(podcast);

        podcast.addPodcaster(secondPodcaster);
        podcastServiceImp.save(podcast);
        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);

        assertNotNull(retrievedPodcast);
        assertEquals(podcast, retrievedPodcast);
        assertEquals(2, retrievedPodcast.getPodcasterSet().size());
    }

    @Test
    void save_ShouldUpdatePodcastDetails_WhenTheyAreAdded(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);

        CustomDesign customDesign = new CustomDesign();
        customDesign.setName("NAME");
        customDesign.setCoverImagePath("C_IM");
        customDesign.setProfileImagePath("P_IM");

        podcast.setDescription("DESC");
        podcast.setCreationDate(LocalDate.now());
        podcast.setCustomDesign(customDesign);
        podcast.setGenreSet(Set.of(podcastGenre));

        podcastServiceImp.save(podcast);
        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);

        assertNotNull(retrievedPodcast);
        assertEquals(podcast, retrievedPodcast);
        assertEquals(customDesign, retrievedPodcast.getCustomDesign());
    }

    @Test
    void delete_ShouldRemovePodcastAndAllEpisodesInPodcast(){
        Podcaster podcaster = new Podcaster();
        podcasterService.save(podcaster);

        Podcast podcast = new Podcast();
        podcast.addPodcaster(podcaster);

        Episode firstEpisode = new Episode();
        firstEpisode.setTitle("EP1");
        Episode secondEpisode = new Episode();
        secondEpisode.setTitle("EP2");
        Episode thirdEpisode = new Episode();
        thirdEpisode.setTitle("EP3");
        podcast.addEpisode(firstEpisode);
        podcast.addEpisode(secondEpisode);
        podcast.addEpisode(thirdEpisode);

        podcastServiceImp.save(podcast);
        podcastServiceImp.delete(podcast);
        Podcast retrievedPodcast = podcastServiceImp.findById(podcast.getId()).orElse(null);
        Episode firstEpisodeRetrieved = episodeService.findById(firstEpisode.getId()).orElse(null);
        Episode secondEpisodeRetrieved = episodeService.findById(secondEpisode.getId()).orElse(null);
        Episode thirdEpisodeRetrieved = episodeService.findById(thirdEpisode.getId()).orElse(null);

        assertNull(retrievedPodcast);
        assertNull(firstEpisodeRetrieved);
        assertNull(secondEpisodeRetrieved);
        assertNull(thirdEpisodeRetrieved);
    }
}