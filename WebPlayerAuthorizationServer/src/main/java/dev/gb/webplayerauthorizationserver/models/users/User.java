package dev.gb.webplayerauthorizationserver.models.users;

import dev.gb.webplayerauthorizationserver.models.users.validators.ValidUserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<Role> roleSet = new HashSet<>();

    private boolean emailVerified = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id
                && emailVerified == user.emailVerified
                && email.equals(user.email)
                && username.equals(user.username)
                && password.equals(user.password)
                && userType == user.userType
                && Objects.equals(roleSet, user.roleSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, userType, roleSet, emailVerified);
    }
}
