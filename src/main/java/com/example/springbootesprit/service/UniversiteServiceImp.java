package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.UniversiteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor

public class UniversiteServiceImp implements IUniversiteService{



    UniversiteRepository universiteRepository;
    @Override
    public Universite addUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public Universite getUniversiteById(Long id) {
        return universiteRepository.findById(id).get();
    }

    @Override
    public Universite update(Universite universite) {
        {
            Universite uni= universiteRepository.findById(universite.getIdUniversite()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + universite.getIdUniversite() + " was found!"));
            if (uni!=null){
                universiteRepository.save(universite);}
            return uni;
        }
    }

    @Override
    public void delete(Long id) {
        universiteRepository.deleteById(id);
    }

    @Override
    public List<Universite> getAllUniversite() {
        return  universiteRepository.findAll();
    }
}
