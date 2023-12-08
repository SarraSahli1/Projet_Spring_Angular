package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    @Query("SELECT COUNT(e) FROM Etudiant e WHERE SIZE(e.reservations) > 0")
    long countDistinctByReservationsIsNotNull();
}
