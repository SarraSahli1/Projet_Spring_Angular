package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.service.IUniversiteService;
import com.example.springbootesprit.service.UniversiteServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Universite")
public class UniversiteController {

    IUniversiteService universiteService;

    @GetMapping ("/retrive-all-universites")
    public List<Universite> getUniversites() {
        List<Universite> universiteList = universiteService.getAllUniversite();
        return universiteList;
    }

    @GetMapping ("/retrieve-universite/{universite-id}")
    public  Universite retrieveUniversite (@PathVariable("universite-id") Long universiteId){
        return universiteService.getUniversiteById(universiteId);
    }

    @PostMapping ("/add-universite")
    public Universite addUniversite (@RequestBody Universite u) {
        Universite universite=universiteService.addUniversite(u);
        return universite;
    }

    @DeleteMapping ("/remove-universite/{universite-id}")
    public void removeUniversite (@PathVariable ("universite-id") Long universiteId) {
        universiteService.delete(universiteId);
    }

    @PutMapping("/update-universite")
    public Universite updateUniversite (@RequestBody Universite u) {
        Universite universite=universiteService.update(u);
        return universite;
    }


}