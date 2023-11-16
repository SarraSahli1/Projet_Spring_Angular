package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.service.IFoyerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class FoyerController {
    IFoyerService iFoyerService;
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
    @PutMapping("/updateFoyer")
    Foyer updateFoyer(@PathVariable Foyer foyer )
    {
        return iFoyerService.update(foyer);
    }
    @DeleteMapping ("/deleteFoyer/{id}")
    void DeleteFoyer(@PathVariable Long id)
    {
        iFoyerService.delete(id);
    }
}
