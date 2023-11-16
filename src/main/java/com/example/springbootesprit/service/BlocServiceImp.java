package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.repositories.BlocRepository;
import com.example.springbootesprit.repositories.FoyerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlocServiceImp implements IBlocService{
    @Autowired
    BlocRepository blocRepository;
    @Autowired
    FoyerRepository foyerRepository;



    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }




    @Override
    public Optional<Bloc> findById(Long id) {
        return blocRepository.findById(id);
    }
    @Override
    public Bloc getBlocById(Long id) {
        return blocRepository.findById(id).get();
    }

    public Bloc getBloc(long idBloc) {
        return blocRepository.findById(idBloc).orElse(null);
    }


    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public void deleteBloc(Bloc b) {
        blocRepository.delete(b);
    }



    @Override
    public Bloc update(Bloc bloc)
    {
        Bloc b= blocRepository.findById(bloc.getIdBloc()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + bloc.getIdBloc() + " was found!"));
        if (b!=null){
        blocRepository.save(bloc);}
        return b;
        }


    @Override
    public void delete(Long id) {
        blocRepository.deleteById(id);
    }




}
