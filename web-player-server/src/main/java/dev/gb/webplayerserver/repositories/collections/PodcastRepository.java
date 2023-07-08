package dev.gb.webplayerserver.repositories.collections;

import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
}
