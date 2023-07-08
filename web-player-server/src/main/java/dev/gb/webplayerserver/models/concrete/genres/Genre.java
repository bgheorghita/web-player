package dev.gb.webplayerserver.models.concrete.genres;

import dev.gb.webplayerserver.models.base.BaseEntity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Genre extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
