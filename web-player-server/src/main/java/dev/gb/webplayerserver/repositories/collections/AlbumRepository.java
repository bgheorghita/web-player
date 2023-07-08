package dev.gb.webplayerserver.repositories.collections;


import dev.gb.webplayerserver.models.concrete.collections.Album;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlbumRepository extends JpaRepository<Album, Long> {
}
