package dev.gb.webplayerserver.models.base;

import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.models.concrete.profiles.ProfileOwner;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Profile extends BaseEntity {
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ProfileOwner profileOwner;
    @Embedded
    private CustomDesign customDesign;
    private LocalDate creationDate;

    public CustomDesign getCustomDesign() {
        return customDesign;
    }

    public void setCustomDesign(CustomDesign customDesign) {
        this.customDesign = customDesign;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate createdDate) {
        this.creationDate = createdDate;
    }

    public ProfileOwner getProfileOwner() {
        return profileOwner;
    }

    public void setProfileOwner(ProfileOwner profileOwner) {
        this.profileOwner = profileOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(customDesign, profile.customDesign)
                && Objects.equals(profileOwner, profile.profileOwner)
                && Objects.equals(creationDate, profile.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customDesign, profileOwner, creationDate);
    }
}