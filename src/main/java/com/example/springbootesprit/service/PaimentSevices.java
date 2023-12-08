package com.example.springbootesprit.service;

import com.example.springbootesprit.controller.PaiementRequest;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.TransactionPaiement;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.FoyerRepository;
import com.example.springbootesprit.repositories.TransactionPaiementRepository;
import com.example.springbootesprit.repositories.UniversiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
@Service
@Transactional
    public class PaimentSevices implements IPaimentServices {

    FoyerServiceImp foyerServices;
    UniversiteServiceImp universiteServices;
        @Autowired
        private TransactionPaiementRepository transactionPaiementRepository;

        @Autowired
        private FoyerRepository foyerRepository;

        @Autowired
        private UniversiteRepository universiteRepository;


/*
    public TransactionPaiement effectuerPaiement(Foyer foyer, Universite universite, BigDecimal montantUniversite, BigDecimal montantFoyer) {
        // Mettez à jour les montants de paiement dans le foyer et l'université
        foyerServices.updateMontantPaiementFoyer(foyer, montantFoyer);
        universiteServices.updateMontantPaiementUniversite(universite, montantUniversite);

        // Créez et sauvegardez l'entité TransactionPaiement
        TransactionPaiement transactionEntity = new TransactionPaiement();
        transactionEntity.setFoyer(foyer);
        transactionEntity.setUniversite(universite);

        // Calculez la somme des montants d'université et de foyer
        BigDecimal montantTotal = montantUniversite.add(montantFoyer);
        transactionEntity.setMontant(montantTotal);

        transactionPaiementRepository.save(transactionEntity);

        return transactionEntity;
    }
/*

    /*
    public TransactionPaiement effectuerPaiement(Foyer foyer, Universite universite, BigDecimal montant) {
        // Synchronisez les montants de paiement
        BigDecimal montantPaiementFoyer = foyer.getMontantPaiement() != null ? foyer.getMontantPaiement() : BigDecimal.ZERO;
        BigDecimal montantPaiementUniversite = universite.getMontantPaiement() != null ? universite.getMontantPaiement() : BigDecimal.ZERO;

        // Créez et sauvegardez l'entité TransactionPaiement
        TransactionPaiement transactionEntity = new TransactionPaiement();
        transactionEntity.setFoyer(foyer);
        transactionEntity.setUniversite(universite);
        transactionEntity.setMontant(montant);

        transactionPaiementRepository.save(transactionEntity);

        return transactionEntity;
    }

*/

    @Override
    public TransactionPaiement effectuerPaiement(PaiementRequest paiementRequest) {
        // Extract foyer, universite, and montant from paiementRequest
        Foyer foyer = paiementRequest.getFoyer();
        Universite universite = paiementRequest.getUniversite();

        // Add the existing montant from paiementRequest
        BigDecimal montant = paiementRequest.getMontant() != null ? paiementRequest.getMontant() : BigDecimal.ZERO;

        // Update the logic to calculate additional amounts based on your requirements
        if (foyer != null && foyer.getMontantPaiement() != null) {
            montant = montant.add(foyer.getMontantPaiement());
        }

        if (universite != null && universite.getMontantPaiement() != null) {
            montant = montant.add(universite.getMontantPaiement());
        }

        // Create and save the TransactionPaiement entity
        TransactionPaiement transactionEntity = new TransactionPaiement();
        transactionEntity.setFoyer(foyer);
        transactionEntity.setUniversite(universite);
        transactionEntity.setMontant(montant);

        // Save the transactionEntity without updating the montantPaiement values in foyer and universite
        transactionPaiementRepository.save(transactionEntity);

        return transactionEntity;
    }


    @Override
    public List<TransactionPaiement> historiquePaiementsParFoyer(Foyer foyer) {
        return transactionPaiementRepository.findByFoyer(foyer);
    }

    @Override
    public List<TransactionPaiement> historiquePaiementsParUniversite(Universite universite) {
        return transactionPaiementRepository.findByUniversite(universite);
    }

}


