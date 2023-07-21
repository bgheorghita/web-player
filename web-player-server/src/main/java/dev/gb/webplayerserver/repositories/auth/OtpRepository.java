package dev.gb.webplayerserver.repositories.auth;

import dev.gb.webplayerserver.models.concrete.auth.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUserEmail(String userEmail);
}
