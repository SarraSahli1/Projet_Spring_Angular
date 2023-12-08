package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Foyer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBlocService {
    Bloc addBloc(Bloc bloc);
    Optional<Bloc> findById(Long id);
    Bloc update(Bloc bloc);
    void delete(Long id);
    Bloc getBlocById(Long id);
     List<Bloc> getAllBlocs();
     void deleteBloc(Bloc b);

    public Foyer addBlocWithFoyer(Foyer foyer) ;
    public List<Bloc> rechercherParNom(String partieNom);
    public Map<String, Double> calculatePercentageByBloc(long idBloc);

    public List<Bloc> rechercherBloc(String partieNom);
    public int getNombreChambresReserveesSurBloc(long idBloc);
    public boolean estBlocComplet(long idBloc);
    public void affecterBlocAuFoyer(long idBloc, long idFoyer) ;

    }
