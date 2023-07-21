package dev.gb.webplayerserver.models.security;

import dev.gb.webplayerserver.models.users.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue
    private long id;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "ROLE_AUTHORITY",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID")
    )
    private final Set<Authority> authoritySet = new HashSet<>();
    @ManyToMany(mappedBy = "roleSet")
    private final Set<User> userSet = new HashSet<>();

    public void addAuthority(Authority authority){
        authoritySet.add(authority);
    }

    public void removeAuthority(Authority authority){
        authoritySet.remove(authority);
    }

    public Set<Authority> getAuthoritySet() {
        return new HashSet<>(authoritySet);
    }

    public Set<User> getUserSet() {
        return new HashSet<>(userSet);
    }
}
