package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;

import java.util.List;
import java.util.Optional;

public interface IBlocService {
    Bloc addBloc(Bloc bloc);
    Optional<Bloc> findById(Long id);
    Bloc update(Bloc bloc);
    void delete(Long id);
    Bloc getBlocById(Long id);
     List<Bloc> getAllBlocs();
     void deleteBloc(Bloc b);


}
