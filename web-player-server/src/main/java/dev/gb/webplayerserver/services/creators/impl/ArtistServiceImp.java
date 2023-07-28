package dev.gb.webplayerserver.services.creators.impl;

import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.services.creators.ArtistService;
import dev.gb.webplayerserver.services.crud.CrudServiceImp;
import org.springframework.stereotype.Service;


@Service
public class ArtistServiceImp extends CrudServiceImp<Artist, Long> implements ArtistService {
}
