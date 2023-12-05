package com.example.springbootesprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("etudiants")

    public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long idReservation;
    @Temporal(TemporalType.DATE)
        Date anneeUniversite;
        boolean estValide;
        String commentaire;

@ManyToMany(mappedBy = "reservations",cascade = CascadeType.ALL ,fetch=FetchType.EAGER)
@JsonIgnore
Set<Etudiant>etudiants;
@ManyToOne
@JoinColumn(name = "chambre_id")
    Chambre chambre;



}


