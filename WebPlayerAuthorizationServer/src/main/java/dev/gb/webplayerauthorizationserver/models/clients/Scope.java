package dev.gb.webplayerauthorizationserver.models.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scopes")
@Getter
@Setter
public class Scope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String scope;
}
