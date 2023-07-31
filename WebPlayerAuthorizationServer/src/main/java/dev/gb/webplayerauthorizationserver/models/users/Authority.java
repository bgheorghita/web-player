package dev.gb.webplayerauthorizationserver.models.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authoritySet", fetch = FetchType.EAGER)
    private final Set<Role> roleSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return id == authority.id && name.equals(authority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
