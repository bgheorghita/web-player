package dev.gb.webplayerserver.services.concrete.audio.impl;

import dev.gb.webplayerserver.models.concrete.audio.Episode;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import dev.gb.webplayerserver.services.concrete.audio.EpisodeService;
import org.springframework.stereotype.Service;

@Service
public class EpisodeServiceImp extends CrudServiceImp<Episode, Long> implements EpisodeService {
    @Override
    public Episode save(Episode episode){
        validateAssociatedPodcast(episode);
        return super.save(episode);
    }

    private void validateAssociatedPodcast(Episode episode) {
        if(episode.getPodcast() == null){
            throw new IllegalStateException("Episode must be part of a podcast.");
        }
        if(episode.getPodcast().getId() == null){
            throw new IllegalStateException("Podcast must be saved first.");
        }
    }
}
