package dev.gb.webplayerserver.services.audio.impl;

import dev.gb.webplayerserver.models.concrete.audio.Single;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.services.audio.SingleService;
import dev.gb.webplayerserver.services.crud.CrudServiceImp;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SingleServiceImp extends CrudServiceImp<Single, Long> implements SingleService {
    @Override
    public Single save(Single single){
        validateAssociatedArtists(single);
        return super.save(single);
    }

    private void validateAssociatedArtists(Single single) {
        Set<Artist> associatedArtists = single.getArtistSet();
        if(associatedArtists.isEmpty()){
            throw new IllegalStateException("Single must have at least one artist.");
        }
        associatedArtists.forEach(artist -> {
            if(artist.getId() == null){
                throw new IllegalStateException("Artist must be saved first.");
            }
        });

    }
}
