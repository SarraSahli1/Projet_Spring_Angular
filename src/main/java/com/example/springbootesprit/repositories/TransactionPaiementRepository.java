package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.TransactionPaiement;
import com.example.springbootesprit.entities.Universite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface TransactionPaiementRepository extends JpaRepository<TransactionPaiement,Long> {
    List<TransactionPaiement> findByFoyer(Foyer foyer);
    List<TransactionPaiement> findByUniversite(Universite universite);
}
