package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.FoyerRepository;
import com.example.springbootesprit.service.FoyerServiceImp;
import com.example.springbootesprit.service.IFoyerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor

public class FoyerController {
    IFoyerService iFoyerService;
    FoyerServiceImp foyerServiceImp;
    FoyerRepository foyerRepository;
    @PostMapping("/addFoyer")
    Foyer addFoyer(@RequestBody Foyer foyer)
    {
        return iFoyerService.addFoyer(foyer);
    }
    @GetMapping("/allFoyer")
    List<Foyer>allFoyer(){
        return iFoyerService.getFoyer();
    }
    @GetMapping("/allFoyer/{id}")
    Foyer foyerById(@PathVariable Long id)
    {
        return iFoyerService.getFoyerById(id);
    }

    @PutMapping("/update-foyer/{id}")
    Foyer updateFoyer(@RequestBody Foyer foyer, @PathVariable Long id)
    {
        foyer.setIdFoyer(id);
        return iFoyerService.update(foyer);
    }
    @DeleteMapping ("/deleteFoyer/{id}")
    void DeleteFoyer(@PathVariable Long id)
    {
        iFoyerService.delete(id);
    }

    @PutMapping("/setfoyer/{idfoyer}/{nomuniversite}")
    public Universite setFoyer(@PathVariable("idfoyer") Long foyerId ,
                               @PathVariable("nomuniversite")String nomUniversite)
    {
        Universite universite=foyerServiceImp.affecterFoyerAUniversite(foyerId,nomUniversite);
        return universite;
    }

    @PutMapping("/unsetfoyer/{idfoyer}")
    public Universite unsetFoyer(@PathVariable("idfoyer") Long foyerId)
    {
        Universite universite=foyerServiceImp.desaffecterFoyerAUniversite(foyerId);
        return universite;
    }
}