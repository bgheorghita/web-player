package dev.gb.webplayerserver.services.concrete.creators.impl;

import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import dev.gb.webplayerserver.services.concrete.creators.ArtistService;
import org.springframework.stereotype.Service;


@Service
public class ArtistServiceImp extends CrudServiceImp<Artist, Long> implements ArtistService {
}
