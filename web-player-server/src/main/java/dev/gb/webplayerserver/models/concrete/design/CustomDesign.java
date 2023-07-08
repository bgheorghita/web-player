package dev.gb.webplayerserver.models.concrete.design;

import java.util.Objects;

public class CustomDesign {
    private String name;
    private String coverImagePath;
    private String profileImagePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomDesign that = (CustomDesign) o;
        return Objects.equals(name, that.name) && Objects.equals(coverImagePath, that.coverImagePath) && Objects.equals(profileImagePath, that.profileImagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coverImagePath, profileImagePath);
    }
}
