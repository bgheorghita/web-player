package dev.gb.webplayerserver.controllers;

import dev.gb.webplayerserver.dtos.singles.creators.CustomDesignDto;
import dev.gb.webplayerserver.mappers.CustomDesignMapper;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.services.creators.ArtistService;
import dev.gb.webplayerserver.utils.JwtClaimsExtractorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/become-artist")
    @ResponseStatus(HttpStatus.CREATED)
    public void createArtist(Authentication authentication,
                             @RequestBody CustomDesignDto customDesignDto) {
        String owner = JwtClaimsExtractorUtil.getUsername(authentication);
        CustomDesign customDesign = CustomDesignMapper.toCustomDesign(customDesignDto);
        artistService.createArtist(owner, customDesign);
    }
}
