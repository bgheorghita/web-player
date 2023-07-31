package dev.gb.webplayerauthorizationserver.models.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "redirect_uris")
@Getter
@Setter
public class RedirectUri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String uri;

    @ManyToOne
    private Client client;
}
