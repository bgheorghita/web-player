package dev.gb.webplayerserver.models.base;

import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import dev.gb.webplayerserver.models.concrete.creators.CreatorOwner;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Creator extends BaseEntity {
    @ManyToOne(cascade = CascadeType.PERSIST)
    private CreatorOwner creatorOwner;
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

    public CreatorOwner getCreatorOwner() {
        return creatorOwner;
    }

    public void setCreatorOwner(CreatorOwner creatorOwner) {
        this.creatorOwner = creatorOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Creator creator = (Creator) o;
        return Objects.equals(customDesign, creator.customDesign)
                && Objects.equals(creatorOwner, creator.creatorOwner)
                && Objects.equals(creationDate, creator.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customDesign, creatorOwner, creationDate);
    }
}