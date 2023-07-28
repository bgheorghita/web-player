package dev.gb.webplayerserver.services.genres;

import dev.gb.webplayerserver.models.concrete.genres.TrackGenre;
import dev.gb.webplayerserver.services.crud.CrudServiceImp;
import org.springframework.stereotype.Service;

@Service
public class TrackGenreServiceImp extends CrudServiceImp<TrackGenre, Long> implements GenreService {
}
