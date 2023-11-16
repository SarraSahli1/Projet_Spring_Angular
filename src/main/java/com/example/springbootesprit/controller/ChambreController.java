package com.example.springbootesprit.controller;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.service.ChambreServiceImp;
import com.example.springbootesprit.service.IChambreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
    @CrossOrigin("*")
public class ChambreController {
    @Autowired
    ChambreServiceImp iChambreService;
    @PostMapping("/addChambre")
    Chambre addChambre(@RequestBody  Chambre chambre){
        return iChambreService.addChambre(chambre);
    }
    @GetMapping("/allChambre")
    List<Chambre>allChambre(){
        return iChambreService.getChambre();
    }

    @GetMapping("/chambre/{id}")
   Chambre retrieveBloc(@PathVariable Long id)
    {
        return iChambreService.getChambreById(id);
    }

    @PutMapping("/updateChambre")
    Chambre updateChambre(@RequestBody Chambre chambre)
    {
      return  iChambreService.update(chambre);
    }
    @PutMapping("/updateChambre/{id}")
    public Chambre updateChambreId(@RequestBody Chambre chambre,@PathVariable Long id) {
        chambre.setIdChambre(id);
        return iChambreService.update(chambre);
    }
    @DeleteMapping("/deleteChambre/{id}")
    void deleteChambre(@PathVariable Long id)
    {
        iChambreService.delete(id);
    }
    @DeleteMapping("/deleteChambre")
    void deleteChambre(@RequestBody Chambre chambre) {
        iChambreService.deleteChambre(chambre);
    }
    @PutMapping("/affecterChambreABloc/{idChambre}/{idBloc}")
    String affecterChambreABloc(@PathVariable long idChambre, @PathVariable long idBloc)
    {
        return iChambreService.AffecterChambreABloc(idChambre,idBloc);
    }
    @PutMapping("/desaffecterChambreABloc/{idChambre}")
    String desaffecterChambreABloc(@PathVariable long idChambre)
    {
        return iChambreService.desaffecterChambreDeBloc(idChambre);
    }
}
