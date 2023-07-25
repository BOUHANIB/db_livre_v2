package ma.emsi.db_livre.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Exposant {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long exposantId;
        @NotEmpty
        private String nom;
        private String pays;
        private String mail;
        private String telephone;
        private String siteWeb;
        private String adresse;
        private String responsableSalle;
        private String responsable;
        private String specialite;
        @NotEmpty
        private String localisation;

        @OneToMany(mappedBy = "exposant" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
        private Set<Livre> livre;

    }

