package dev.gb.webplayerserver.services.concrete.genres;

import dev.gb.webplayerserver.models.concrete.genres.PodcastGenre;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import org.springframework.stereotype.Service;

@Service
public class PodcastGenreServiceImp extends CrudServiceImp<PodcastGenre, Long> implements PodcastGenreService {
}
