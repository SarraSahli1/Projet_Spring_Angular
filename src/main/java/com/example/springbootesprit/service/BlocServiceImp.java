package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.TypeChambre;
import com.example.springbootesprit.repositories.BlocRepository;
import com.example.springbootesprit.repositories.FoyerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
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

    @Override
    public Foyer addBlocWithFoyer(Foyer foyer) {
        //sauvgarder  l'objet fils foyer
        Foyer foyerr =foyerRepository.save(foyer);
        //parcourir la liste de bloc (parent)
        foyerr.getBlocs().forEach(bloc -> {
            //affecter le child foyer au parent bloc et sauvgarder parent
            bloc.setFoyers(foyerr);
            blocRepository.save(bloc);
        });
        return foyerr;
    }
    @Transactional
    public void affecterBlocAuFoyer(long idBloc, long idFoyer) {
        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(() -> new RuntimeException("Bloc non trouvé avec l'ID : " + idBloc));
        Foyer foyer = foyerRepository.findById(idFoyer).orElseThrow(() -> new RuntimeException("Foyer non trouvé avec l'ID : " + idFoyer));

        // Assurez-vous que le bloc n'est pas déjà associé à un foyer
        if (bloc.getFoyers() != null) {
            throw new RuntimeException("Le bloc est déjà associé à un foyer.");
        }

        // Associer le bloc au foyer
        bloc.setFoyers(foyer);
        blocRepository.save(bloc);
    }

    @Override
    public List<Bloc> rechercherParNom(String partieNom) {
        return blocRepository.findByNomBlocContaining(partieNom);
    }

@Override
    public List<Bloc> rechercherBloc(String partieNom) {
        return (List<Bloc>) blocRepository.findByNomBloc(partieNom);
    }
    @Override
    public String calculatePercentageByBloc(String nomBloc) {
        Bloc bloc = blocRepository.findByNomBloc(nomBloc);

        if (bloc == null) {
            return "Bloc not found";
        }

        long totalChambres = bloc.getChambres().size();

        if (totalChambres == 0) {
            return "No rooms in the bloc";
        }

        long simpleCount = bloc.getChambres().stream().filter(chambre -> chambre.getTypeC() == TypeChambre.SIMPLE).count();
        long doubleCount = bloc.getChambres().stream().filter(chambre -> chambre.getTypeC() == TypeChambre.DOUBLE).count();
        long tripleCount = bloc.getChambres().stream().filter(chambre -> chambre.getTypeC() == TypeChambre.TRIPLE).count();

        double simplePercentage = (double) simpleCount / totalChambres * 100;
        double doublePercentage = (double) doubleCount / totalChambres * 100;
        double triplePercentage = (double) tripleCount / totalChambres * 100;

        return "Bloc " + nomBloc + " - " +
                "Simple: " + simplePercentage + "%, " +
                "Double: " + doublePercentage + "%, " +
                "Triple: " + triplePercentage + "%";
    }
    @Override
    public int getNombreChambresReserveesSurBloc(long idBloc) {
        // Récupérer le bloc par son ID
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);

        // Vérifier si le bloc existe
        if (bloc == null) {
            throw new IllegalArgumentException("Bloc non trouvé avec l'ID : " + idBloc);
        }

        // Récupérer la liste des chambres du bloc
        Set<Chambre> chambresDuBloc = bloc.getChambres();

        // Calculer le nombre total de chambres réservées sur le bloc
        int nombreTotalReservations = 0;
        for (Chambre chambre : chambresDuBloc) {
            nombreTotalReservations += chambre.getReservations().size();
        }

        return nombreTotalReservations;
    }


    @Override
    public boolean estBlocComplet(long idBloc) {
        // Récupérer le bloc par son ID
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);

        // Vérifier si le bloc existe
        if (bloc == null) {
            throw new IllegalArgumentException("Bloc non trouvé avec l'ID : " + idBloc);
        }


       if (getNombreChambresReserveesSurBloc(idBloc)<bloc.getCapaciteBloc() )
           return true;
       else return false;
    }




}
