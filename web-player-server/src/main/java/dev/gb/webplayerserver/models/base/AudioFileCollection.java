package dev.gb.webplayerserver.models.base;

import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AudioFileCollection extends BaseEntity {
    private String title;
    private String description;
    @Embedded
    private CustomDesign customDesign;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomDesign getCustomDesign() {
        return customDesign;
    }

    public void setCustomDesign(CustomDesign customDesign) {
        this.customDesign = customDesign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioFileCollection)) return false;

        AudioFileCollection that = (AudioFileCollection) o;

        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(description, that.description)) return false;
        return Objects.equals(customDesign, that.customDesign);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (customDesign != null ? customDesign.hashCode() : 0);
        return result;
    }
}
