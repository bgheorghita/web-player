package dev.gb.webplayerserver.services.creators;


import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.services.crud.CrudService;


public interface ArtistService extends CrudService<Artist, Long> {
    void createArtist(String ownerUsername, CustomDesign customDesign);
}
