package com.example.springbootesprit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
    @OneToOne
    Universite uni;
@OneToMany (cascade = CascadeType.ALL,mappedBy = "foyers")
Set<Bloc> blocs;



}



