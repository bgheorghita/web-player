package dev.gb.webplayerserver.models.users;

import dev.gb.webplayerserver.models.base.Creator;
import dev.gb.webplayerserver.models.security.Role;
import dev.gb.webplayerserver.models.validators.ValidUserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Email(message = "Email not valid")
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @ValidUserType
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private final Set<Role> roleSet = new HashSet<>();
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "USER_CREATOR",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CREATOR_ID")
    )
    private final Set<Creator> creatorSet = new HashSet<>();
    private boolean emailVerified = false;

    public User() {
    }

    private User(String email, String username, String password, UserType userType, boolean emailVerified) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.emailVerified = emailVerified;
    }

    public void addCreator(Creator creator){
        creatorSet.add(creator);
    }

    public void removeCreator(Creator creator){
        creatorSet.remove(creator);
    }

    public void addRole(Role role){
        roleSet.add(role);
    }

    public void removeRole(Role role){
        roleSet.remove(role);
    }

    public String getEmail() {
        return email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Role> getRoleSet() {
        return new HashSet<>(roleSet);
    }

    public UserType getUserType() {
        return userType;
    }

    public User withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public User withEmailVerified(boolean emailVerified){
        this.emailVerified = emailVerified;
        return this;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified){
        this.emailVerified = emailVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Creator> getCreatorSet() {
        return new HashSet<>(creatorSet);
    }

    public User buildUser() {
        return new User(email, username, password, userType, emailVerified);
    }
}
