package dev.gb.webplayerserver.repositories.creators;

import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcasterRepository extends JpaRepository<Podcaster, Long> {
}
