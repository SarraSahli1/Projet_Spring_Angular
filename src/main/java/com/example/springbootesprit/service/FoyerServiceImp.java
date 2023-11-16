package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.FoyerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FoyerServiceImp implements IFoyerService{
    @Autowired
    FoyerRepository foyerRepository;
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
        {
            Foyer foyer1= foyerRepository.findById(foyer.getIdFoyer()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + foyer.getIdFoyer() + " was found!"));
            if (foyer1!=null){
                foyerRepository.save(foyer);}
            return foyer1;
        }
    }

    @Override
    public void delete(Long id) {
        foyerRepository.deleteById(id);
    }

    @Override
    public Foyer getFoyerById(Long id) {
        return  foyerRepository.findById(id).get();
    }




}