package dev.gb.webplayerauthorizationserver.repositories;

import dev.gb.webplayerauthorizationserver.models.otps.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query("""
        SELECT o FROM Otp o WHERE o.userEmail = :userEmail
    """)
    Optional<Otp> findByUserEmail(String userEmail);
}
