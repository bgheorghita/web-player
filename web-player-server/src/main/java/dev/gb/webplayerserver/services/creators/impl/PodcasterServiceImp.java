package dev.gb.webplayerserver.services.creators.impl;

import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import dev.gb.webplayerserver.services.creators.PodcasterService;
import dev.gb.webplayerserver.services.crud.CrudServiceImp;
import org.springframework.stereotype.Service;

@Service
public class PodcasterServiceImp extends CrudServiceImp<Podcaster, Long> implements PodcasterService {
}
