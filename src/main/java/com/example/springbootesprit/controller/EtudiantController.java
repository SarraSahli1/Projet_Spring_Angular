package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Etudiant;
import com.example.springbootesprit.service.IEtudiantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")

public class EtudiantController {

    IEtudiantService iEtudiantService;

    @PostMapping("/addEtudiant")
    public Etudiant addEtudiant(@RequestBody Etudiant e) {
        Etudiant etudiant = iEtudiantService.addEtudiant(e);
        return etudiant;
    }
    @GetMapping("/allEtudiant")
    List<Etudiant>allEtudiant(){
        return iEtudiantService.getEtudiant();
    }
    @GetMapping("/Etudiant/{id}")
    Etudiant  EtudiantById(@PathVariable Long id)
    {
        return iEtudiantService.getEtudiantById(id);
    }
    @DeleteMapping("/deleteEtudiant/{id}")
    void delete(@PathVariable Long id)
    {
        iEtudiantService.delete(id);
    }
    @PutMapping("/updateEtudiant")
    Etudiant update(@RequestBody Etudiant etudiant)
    {
        return iEtudiantService.update(etudiant);
    }
}
