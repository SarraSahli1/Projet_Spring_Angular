package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Universite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversiteRepository extends JpaRepository<Universite,Long> {
}
