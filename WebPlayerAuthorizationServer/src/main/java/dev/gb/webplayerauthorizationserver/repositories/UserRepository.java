package dev.gb.webplayerauthorizationserver.repositories;

import dev.gb.webplayerauthorizationserver.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
        SELECT u FROM User u WHERE u.email = :email
    """)
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT u FROM User u WHERE u.username = :username
    """)
    Optional<User> findByUsername(String username);

    @Query("""
        SELECt u FROM User u JOIN u.roleSet r WHERE r.name = :roleName
    """)
    Set<User> findByRoleName(String roleName);
}
