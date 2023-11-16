package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;

import java.util.List;
import java.util.Optional;

public interface IFoyerService {
    Foyer addFoyer(Foyer foyer);
    List<Foyer> getFoyer();
    Optional<Foyer> findById(Long id);
    Foyer update(Foyer foyer);
    void delete(Long id);
    Foyer getFoyerById(Long id);
    Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite);
    Universite desaffecterFoyerAUniversite (long idFoyer);
}
