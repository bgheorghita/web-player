package dev.gb.webplayerauthorizationserver.models.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authentication_methods")
@Getter
@Setter
public class AuthenticationMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authenticationMethod;
}
