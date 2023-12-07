package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.FoyerRepository;
import com.example.springbootesprit.repositories.UniversiteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FoyerServiceImp implements IFoyerService{
    @Autowired
    FoyerRepository foyerRepository;
    @Autowired
    UniversiteRepository universiteRepository;
    @Override
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public List<Foyer> getFoyer() {
        return foyerRepository.findAll();
    }

    @Override
    public Optional<Foyer> findById(Long id) {
        return  foyerRepository.findById(id);
    }

    @Override
    public Foyer update(Foyer foyer) {
        Long id = foyer.getIdFoyer();
        Foyer existingReservation = foyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No foyer with id " + id + " was found!"));

        // Update existingReservation with the values from the provided reservation
        existingReservation.setNomFoyer(foyer.getNomFoyer());
        existingReservation.setArchived(foyer.getArchived());
        existingReservation.setCapaciteFoyer(foyer.getCapaciteFoyer());
        // Add other property updates...

        foyerRepository.save(existingReservation);

        return existingReservation;
    }
    @Override
    public void delete(Long id) {
        foyerRepository.deleteById(id);
    }

    @Override
    public Foyer getFoyerById(Long id) {
        return  foyerRepository.findById(id).get();
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Universite uni = universiteRepository.findUniversiteByNomUniversite(nomUniversite);
        Foyer foyer = foyerRepository.findById(idFoyer).get();
        foyer.setUni(uni);
        foyerRepository.save(foyer);
        return uni;
    }
    @Override
    public Universite desaffecterFoyerAUniversite (long idFoyer){
        Foyer foyer = foyerRepository.findById(idFoyer).get();
        foyer.setUni(null);
        foyerRepository.save(foyer);
        return foyer.getUni();
    }



}