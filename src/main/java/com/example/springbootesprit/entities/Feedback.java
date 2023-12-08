package com.example.springbootesprit.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentaire;
    @ManyToOne
    @JoinColumn(name = "idfoyer")
    private Foyer foyer ;

    @ManyToOne
    @JoinColumn(name = "idBUniversite")
    private Universite universite;
    public Feedback(Long id, String commentaire, Universite universite, Foyer foyer) {
        this.id = id;
        this.commentaire = commentaire;
        this.universite = universite;
        this.foyer = foyer;
    }

    public Long getId() {
        return id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Universite getUniversite() {
        return universite;
    }

    public Foyer getFoyer() {
        return foyer;
    }


}



