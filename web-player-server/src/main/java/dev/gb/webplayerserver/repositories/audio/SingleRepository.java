package dev.gb.webplayerserver.repositories.audio;

import dev.gb.webplayerserver.models.concrete.audio.Single;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleRepository extends JpaRepository<Single, Long> {
}
