package com.example.springbootesprit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "transaction_paiement")
public class TransactionPaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "foyer_id")
    private Foyer foyer;

    @ManyToOne
    @JoinColumn(name = "universite_id")
    private Universite universite;

    private BigDecimal montant;

    public String getTypePaiement() {
        // Check the transaction amount to determine the payment type
        if (montant.compareTo(BigDecimal.valueOf(50)) <= 0) {
            return "Carte bancaire";
        } else if (montant.compareTo(BigDecimal.valueOf(100)) <= 0 && montant.compareTo(BigDecimal.valueOf(50)) <= 0) {
            return "Virement";
        } else {
            // If the amount is greater than 100, you may need to check additional
            // transaction details or retrieve the payment type from another data source
            return "Espèces";
        }
    }}
