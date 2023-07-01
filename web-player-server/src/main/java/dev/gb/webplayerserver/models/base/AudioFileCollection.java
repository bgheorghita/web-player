package dev.gb.webplayerserver.models.base;

import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
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
}
