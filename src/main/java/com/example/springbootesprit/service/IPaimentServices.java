package com.example.springbootesprit.service;



import com.example.springbootesprit.controller.PaiementRequest;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.TransactionPaiement;
import com.example.springbootesprit.entities.Universite;

import java.util.List;

public interface IPaimentServices {
    TransactionPaiement effectuerPaiement(PaiementRequest paiementRequest);



    List<TransactionPaiement> historiquePaiementsParFoyer(Foyer foyer);
    List<TransactionPaiement> historiquePaiementsParUniversite(Universite universite);}

