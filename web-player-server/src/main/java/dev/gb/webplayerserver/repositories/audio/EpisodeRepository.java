package dev.gb.webplayerserver.repositories.audio;

import dev.gb.webplayerserver.models.concrete.audio.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode,  Long> {
}
