package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Etudiant;
import com.example.springbootesprit.repositories.EtudiantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class EtudiantServiceImp implements IEtudiantService{
    @Autowired
    EtudiantRepository etudiantRepository;

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public List<Etudiant> getEtudiant() {
        return etudiantRepository.findAll();
    }


    @Override
    public Optional<Etudiant> findById(Long id) {
        return  etudiantRepository.findById(id);
    }

    @Override
    public Etudiant update(Etudiant etudiant) {
        {
            Etudiant et = etudiantRepository.findById(etudiant.getIdEtudiant()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + etudiant.getIdEtudiant() + " was found!"));
            if (et!=null){
                etudiantRepository.save(etudiant);}
            return et;
        }
    }

    @Override
    public void delete(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).get();
    }
}
