package dev.gb.webplayerserver.repositories.genres;

import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackGenreRepository extends JpaRepository<TrackGenre, Long> {
}
