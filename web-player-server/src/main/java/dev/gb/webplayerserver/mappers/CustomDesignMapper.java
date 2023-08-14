package dev.gb.webplayerserver.mappers;

import dev.gb.webplayerserver.dtos.singles.creators.CustomDesignDto;
import dev.gb.webplayerserver.models.concrete.design.CustomDesign;

public class CustomDesignMapper implements DomainMapper<CustomDesignDto, CustomDesign> {
    private CustomDesignMapper(){}

    @Override
    public CustomDesign toDomain(CustomDesignDto customDesignDto) {
        CustomDesign customDesign = new CustomDesign();
        customDesign.setName(customDesignDto.getName());
        customDesign.setCoverImagePath(customDesignDto.getCoverImagePath());
        customDesign.setProfileImagePath(customDesignDto.getProfileImagePath());

        return customDesign;
    }

    public static CustomDesign toCustomDesign(CustomDesignDto customDesignDto) {
        return new CustomDesignMapper().toDomain(customDesignDto);
    }
}
