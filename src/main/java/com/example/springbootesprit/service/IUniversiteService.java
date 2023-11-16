package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Universite;

import java.util.List;

public interface IUniversiteService {
    Universite addUniversite(Universite universite);
    Universite getUniversiteById(Long id);
    Universite update(Universite universite);
    void delete(Long id);
    List<Universite> getAllUniversite();
}
