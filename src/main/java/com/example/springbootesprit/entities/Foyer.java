package com.example.springbootesprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Foyer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idFoyer")
    long idFoyer;
    String nomFoyer;
    long capaciteFoyer;
    private Boolean archived=false;
    private BigDecimal montantPaiement;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Universite uni;

    @JsonIgnore

    @OneToMany(mappedBy = "foyers")
    private Set<Bloc> Blocs;

}


