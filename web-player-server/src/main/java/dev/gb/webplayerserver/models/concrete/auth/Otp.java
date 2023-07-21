package dev.gb.webplayerserver.models.concrete.auth;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
public class Otp {
    public static final int EXPIRES_IN_SECONDS = 300;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    @Column(name = "user_email")
    private String userEmail;
    private LocalDateTime generatedAt;

    public Otp() {
    }

    public Otp(String code, String userEmail, LocalDateTime generatedAt) {
        this.code = code;
        this.userEmail = userEmail;
        this.generatedAt = generatedAt;
    }

    public boolean isExpired(){
        LocalDateTime expirationDate = generatedAt.plusSeconds(EXPIRES_IN_SECONDS);
        return LocalDateTime.now().isAfter(expirationDate);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
