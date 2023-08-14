package dev.gb.webplayerserver.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomDesignDto {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("coverImagePath")
    private final String coverImagePath;
    @JsonProperty("profileImagePath")
    private final String profileImagePath;

    public String getName() {
        return name;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    @JsonCreator
    private CustomDesignDto(CustomDesignDtoBuilder builder) {
        this.name = builder.getName();
        this.coverImagePath = builder.getCoverImagePath();
        this.profileImagePath = builder.getProfileImagePath();
    }

    public static class CustomDesignDtoBuilder {
        private String name;
        private String coverImagePath;
        private String profileImagePath;

        public String getName() {
            return name;
        }

        public String getCoverImagePath() {
            return coverImagePath;
        }

        public String getProfileImagePath() {
            return profileImagePath;
        }

        public CustomDesignDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CustomDesignDtoBuilder withCoverImagePath(String coverImagePath) {
            this.coverImagePath = coverImagePath;
            return this;
        }

        public CustomDesignDtoBuilder withProfileImagePath(String profileImagePath) {
            this.profileImagePath = profileImagePath;
            return this;
        }

        public CustomDesignDto build() {
            return new CustomDesignDto(this);
        }
    }
}
