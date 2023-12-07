package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.service.IBlocService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Bloc")
@CrossOrigin(origins = "http://localhost:4200")

public class BlocController {
    IBlocService blocServiceImp;
    @PostMapping("/addBloc")
    Bloc addBloc( @RequestBody   Bloc bloc)
    {
        return blocServiceImp.addBloc(bloc);
    }
    @GetMapping("/bloc/{id}")
    Bloc retrieveBloc(@PathVariable Long id)
    {
        return blocServiceImp.getBlocById(id);
    }


    @GetMapping("/allBloc")
    List<Bloc>retrieveBlocs(){
        return blocServiceImp.getAllBlocs();
    }

    @DeleteMapping("/deleteBloc/{id}")
    void deleteBloc(@PathVariable Long id)
    {
        blocServiceImp.delete(id);
    }

    @DeleteMapping("/deleteBloc")
    void deleteBloc(@RequestBody Bloc bloc) {
        blocServiceImp.deleteBloc(bloc);
    }
    @PutMapping("/updateBloc")
    Bloc updateBloc(@RequestBody Bloc bloc)
    {
        return blocServiceImp.update(bloc);
    }

    @PutMapping("/updateBlocA/{id}")
    Bloc updateBlocById(@RequestBody Bloc bloc)
    {
        return blocServiceImp.update(bloc);
    }



    @GetMapping("/recherche/{nomBloc}")
    public ResponseEntity<List<Bloc>> rechercheAvanceeBloc(@PathVariable("nomBloc") String nomBloc) {
        System.out.println("Recherche avancée avec nomBloc : " + nomBloc);
        List<Bloc> blocs = blocServiceImp.rechercherParNom(nomBloc);
        return new ResponseEntity<>(blocs, HttpStatus.OK);
    }


    @GetMapping("/nom/{nomBloc}")
    public ResponseEntity<List<Bloc>> getByBloc(@PathVariable String nomBloc) {
        List<Bloc> blocs = blocServiceImp.rechercherBloc(nomBloc);
        return new ResponseEntity<>(blocs, HttpStatus.OK);
    }



    @GetMapping("/percentage/{nomBloc}")
    public String getPercentageByBloc(@PathVariable String nomBloc) {
        return blocServiceImp.calculatePercentageByBloc(nomBloc);
    }

    @PutMapping("/affecterFoyerABloc/{idFoyer}/{idBloc}")
    public void affecterChambreABloc(@PathVariable long idFoyer, @PathVariable long idBloc) {
        blocServiceImp.affecterBlocAuFoyer(idFoyer,idBloc);
    }

    @GetMapping("/countreservationchambre/{idBloc}")
    public int getcountreservationchambre(@PathVariable long idBloc) {
        return blocServiceImp.getNombreChambresReserveesSurBloc(idBloc);
    }


    @GetMapping("/BlocDispo/{idbloc}")
    public Boolean blocdispo(@PathVariable long idbloc){
        return blocServiceImp.estBlocComplet(idbloc);
    }

}