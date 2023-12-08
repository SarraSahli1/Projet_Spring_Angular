package com.example.springbootesprit.controller;


import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;

import java.math.BigDecimal;

public class PaiementRequest {
    private Foyer foyer;
    private Universite universite;
    private BigDecimal montant;

    // Constructors, getters, and setters



    public PaiementRequest(Foyer foyer, Universite universite, BigDecimal montant) {
        this.foyer = foyer;
        this.universite = universite;
        this.montant = montant;
    }

    public Foyer getFoyer() {
        return foyer;
    }

    public void setFoyer(Foyer foyer) {
        this.foyer = foyer;
    }

    public Universite getUniversite() {
        return universite;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
}
