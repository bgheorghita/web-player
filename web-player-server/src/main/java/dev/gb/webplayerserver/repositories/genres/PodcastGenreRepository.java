package dev.gb.webplayerserver.repositories.genres;

import dev.gb.webplayerserver.models.concrete.genres.PodcastGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastGenreRepository extends JpaRepository<PodcastGenre, Long> {
}
