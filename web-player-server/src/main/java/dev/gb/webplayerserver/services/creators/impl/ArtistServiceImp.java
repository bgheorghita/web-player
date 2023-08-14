package dev.gb.webplayerserver.services.creators.impl;

import dev.gb.webplayerserver.exceptions.BadRequestException;
import dev.gb.webplayerserver.models.concrete.creators.Artist;
import dev.gb.webplayerserver.models.concrete.creators.CreatorOwner;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.repositories.creators.ArtistRepository;
import dev.gb.webplayerserver.services.creators.ArtistService;
import dev.gb.webplayerserver.services.crud.CrudServiceImp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ArtistServiceImp extends CrudServiceImp<Artist, Long> implements ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistServiceImp(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public void createArtist(String ownerUsername, CustomDesign customDesign) {
        if(StringUtils.isEmpty(ownerUsername)) {
            throw new BadRequestException("Owner username is mandatory.");
        }

        if(StringUtils.isEmpty(customDesign.getName())){
            throw new BadRequestException("Artist name is mandatory.");
        }

        CreatorOwner creatorOwner = new CreatorOwner();
        creatorOwner.setUsername(ownerUsername);

        String coverImagePath = StringUtils.isEmpty(customDesign.getCoverImagePath())
                ? CustomDesign.DEFAULT_COVER_IMG_PATH
                : customDesign.getCoverImagePath();

        String profileImagePath = StringUtils.isEmpty(customDesign.getProfileImagePath())
                ? CustomDesign.DEFAULT_PROFILE_IMAGE_PATH
                : customDesign.getProfileImagePath();

        customDesign.setCoverImagePath(coverImagePath);
        customDesign.setProfileImagePath(profileImagePath);

        Artist artist = new Artist();
        artist.setCreatorOwner(creatorOwner);
        artist.setCreationDate(LocalDate.now());
        artist.setCustomDesign(customDesign);
        artistRepository.save(artist);
    }
}
