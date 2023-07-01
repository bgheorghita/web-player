package dev.gb.webplayerserver.models.base;

import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Creator extends BaseEntity {
    @Embedded
    private CustomDesign customDesign;
    private LocalDate joinedDate;

    public CustomDesign getCustomDesign() {
        return customDesign;
    }

    public void setCustomDesign(CustomDesign customDesign) {
        this.customDesign = customDesign;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }
}