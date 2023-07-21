package dev.gb.webplayerserver.models.security;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUTHORITIES")
public class Authority {
    @Id
    private String name;
    @ManyToMany(mappedBy = "authoritySet", fetch = FetchType.EAGER)
    private final Set<Role> roleSet = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoleSet() {
        return new HashSet<>(roleSet);
    }
}
