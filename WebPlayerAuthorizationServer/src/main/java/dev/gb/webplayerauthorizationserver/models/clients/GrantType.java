package dev.gb.webplayerauthorizationserver.models.clients;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grant_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "grant_type")
    private String grantType;
}
