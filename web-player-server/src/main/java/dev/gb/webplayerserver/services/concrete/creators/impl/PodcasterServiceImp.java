package dev.gb.webplayerserver.services.concrete.creators.impl;

import dev.gb.webplayerserver.models.concrete.creators.Podcaster;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import dev.gb.webplayerserver.services.concrete.creators.PodcasterService;
import org.springframework.stereotype.Service;

@Service
public class PodcasterServiceImp extends CrudServiceImp<Podcaster, Long> implements PodcasterService {
}
