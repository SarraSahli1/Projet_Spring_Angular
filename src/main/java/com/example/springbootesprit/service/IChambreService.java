package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.TypeChambre;

import java.util.List;
import java.util.Optional;

public interface IChambreService {
    Chambre addChambre(Chambre chambre);
    List<Chambre> getChambre();
    Optional<Chambre> findById(Long id);
    Chambre update(Chambre chambre);
    Chambre updateById(Chambre chambre);

    void delete(Long id);
    void deleteChambre(Chambre b);
    Chambre getChambreById(Long id);
    List<Chambre> getChambresByBloc(Bloc bloc);
    List<Chambre> getChambresByType(TypeChambre typeChambre);
    public void AffecterChambreABloc(long idChambre, long idBloc);


}