package dev.gb.webplayerserver.repositories.creators;

import dev.gb.webplayerserver.models.concrete.creators.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
