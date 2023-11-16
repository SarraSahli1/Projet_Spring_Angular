package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.service.IBlocService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Bloc")

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
    @PutMapping("/updateBloc/{id}")
    Bloc updateBlocById(@RequestBody Bloc bloc)
    {
        return blocServiceImp.update(bloc);
    }

}
