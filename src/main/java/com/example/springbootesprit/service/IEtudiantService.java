package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Etudiant;

import java.util.List;
import java.util.Optional;

public interface IEtudiantService {
    Etudiant addEtudiant(Etudiant etudiant);
    List<Etudiant> getEtudiant();
    Optional<Etudiant> findById(Long id);
    Etudiant update(Etudiant etudiant);
    void delete(Long id);
    Etudiant getEtudiantById(Long id);
}
