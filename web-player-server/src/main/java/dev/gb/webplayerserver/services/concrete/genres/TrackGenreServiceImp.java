package dev.gb.webplayerserver.services.concrete.genres;

import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import org.springframework.stereotype.Service;

@Service
public class TrackGenreServiceImp extends CrudServiceImp<TrackGenre, Long> implements GenreService {
}
