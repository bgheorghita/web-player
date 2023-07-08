package dev.gb.webplayerserver.services.concrete.collections.impl;

import dev.gb.webplayerserver.models.concrete.collections.Podcast;
import dev.gb.webplayerserver.services.base.crud.CrudServiceImp;
import dev.gb.webplayerserver.services.concrete.collections.PodcastService;
import org.springframework.stereotype.Service;

@Service
public class PodcastServiceImp extends CrudServiceImp<Podcast, Long> implements PodcastService {

    @Override
    public Podcast save(Podcast podcast){
        validateAssociatedPodcasters(podcast);
        return super.save(podcast);
    }

    private void validateAssociatedPodcasters(Podcast podcast) {
        if(podcast.getPodcasterSet().isEmpty()){
            throw new IllegalStateException("The podcast must have at least one host.");
        }
        podcast.getPodcasterSet().forEach(podcaster -> {
            if (podcaster.getId() == null){
                throw new IllegalStateException("Podcaster must be saved into database first.");
            }
        });
    }
}
