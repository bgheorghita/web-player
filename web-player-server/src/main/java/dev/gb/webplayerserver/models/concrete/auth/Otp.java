package dev.gb.webplayerserver.models.concrete.auth;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Otp otp)) return false;

        if (id != otp.id) return false;
        if (!Objects.equals(code, otp.code)) return false;
        if (!Objects.equals(userEmail, otp.userEmail)) return false;
        return Objects.equals(generatedAt, otp.generatedAt);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (generatedAt != null ? generatedAt.hashCode() : 0);
        return result;
    }
}
