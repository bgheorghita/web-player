package dev.gb.webplayerserver.models.base;

import dev.gb.webplayerserver.models.concrete.design.CustomDesign;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creator)) return false;
        if (!super.equals(o)) return false;

        Creator creator = (Creator) o;

        if (!Objects.equals(customDesign, creator.customDesign))
            return false;
        return Objects.equals(joinedDate, creator.joinedDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customDesign != null ? customDesign.hashCode() : 0);
        result = 31 * result + (joinedDate != null ? joinedDate.hashCode() : 0);
        return result;
    }
}