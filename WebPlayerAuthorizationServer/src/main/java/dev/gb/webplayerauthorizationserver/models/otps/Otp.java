package dev.gb.webplayerauthorizationserver.models.otps;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "otps")
@Getter
@Setter
public class Otp {
    @Id
    @Column(name = "user_email")
    private String userEmail;
    private String code;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Otp otp = (Otp) o;
        return Objects.equals(userEmail, otp.userEmail) && Objects.equals(code, otp.code) && Objects.equals(generatedAt, otp.generatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, code, generatedAt);
    }
}
