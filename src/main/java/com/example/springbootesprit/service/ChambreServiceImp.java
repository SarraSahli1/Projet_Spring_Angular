package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.repositories.BlocRepository;
import com.example.springbootesprit.repositories.ChambreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ChambreServiceImp implements IChambreService{
    @Autowired
    ChambreRepository chambreRepository;
    @Autowired
    BlocRepository blocRepository;


   public String AffecterChambreABloc(long idChambre, long idBloc) {
        try {
            Optional<Chambre> optionalChambre = chambreRepository.findById(idChambre);
            Optional<Bloc> optionalBloc = blocRepository.findById(idBloc);

            if (optionalChambre.isPresent() && optionalBloc.isPresent()) {
                Chambre chambre = optionalChambre.get();
                Bloc bloc = optionalBloc.get();

                chambre.setBloc(bloc);
                chambreRepository.save(chambre);

                return "Chambre successfully assigned to bloc.";
            } else {
                return "Chambre or Bloc not found.";
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
    public String desaffecterChambreDeBloc(long idChambre) {
        try {
            Optional<Chambre> optionalChambre = chambreRepository.findById(idChambre);

            if (optionalChambre.isPresent()) {
                Chambre chambre = optionalChambre.get();
                chambre.setBloc(null); // Dissocier la chambre du bloc en définissant le bloc à null
                chambreRepository.save(chambre);
                return "Chambre successfully removed from bloc.";
            } else {
                return "Chambre not found.";
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public List<Chambre> getChambre() {
        return chambreRepository.findAll();
    }

    @Override
    public Optional<Chambre> findById(Long id) {
        return  chambreRepository.findById(id);
    }

    @Override
    public Chambre update(Chambre chambre) {
        {
            Chambre chambre1= chambreRepository.findById(chambre.getIdChambre()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + chambre.getIdChambre() + " was found!"));
            if (chambre1!=null){
                chambreRepository.save(chambre);}
            return chambre1;
        }
    }

    @Override
    public Chambre updateById(Chambre chambre)  {
        Chambre chambre1= chambreRepository.findById(chambre.getIdChambre()).orElseThrow(() -> new EntityNotFoundException("No Bloc with id " + chambre.getIdChambre() + " was found!"));
        if (chambre1!=null){
            chambreRepository.save(chambre);}
        return chambre1;
    }

    @Override
    public void delete(Long id) {
        chambreRepository.deleteById(id);
    }

    @Override
    public void deleteChambre(Chambre b) {
        chambreRepository.delete(b);
    }

    @Override
    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id).get();
    }

}
