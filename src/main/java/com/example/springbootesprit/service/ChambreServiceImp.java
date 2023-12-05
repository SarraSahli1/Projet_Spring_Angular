package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.entities.TypeChambre;
import com.example.springbootesprit.repositories.BlocRepository;
import com.example.springbootesprit.repositories.ChambreRepository;
import com.example.springbootesprit.repositories.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chambre or Bloc not found.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage(), e);
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chambre not found.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage(), e);
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

    @Override
    public List<Chambre> getChambresByBloc(Bloc bloc) {
        return chambreRepository.findByBloc(bloc);
    }

    @Override
    public List<Chambre> getChambresByType(TypeChambre typeChambre) {
        return chambreRepository.findByTypeC(typeChambre);
    }

    @Autowired
    ReservationRepository reservationRepository;


    //@Scheduled(cron = "0 * * * * ?") pour le tester a chaque minute
    @Scheduled(cron = "0 0 0 1 6 ?")

    @Transactional
    public void libererChambresApresDateLimite() {
        LocalDate dateLimite = LocalDate.now().withMonth(6).withDayOfMonth(1); // 1er juin de cette année

        // Conversion de LocalDate en Date
        Date dateLimiteAsDate = java.sql.Date.valueOf(dateLimite);

        List<Chambre> chambresAvecDateLimiteAtteinte = chambreRepository.findByReservationsAnneeUniversiteBeforeAndReservationsIsNotNull(dateLimiteAsDate);

        for (Chambre chambre : chambresAvecDateLimiteAtteinte) {
            // Libérer la chambre en supprimant les réservations
            chambre.getReservations().clear();
            chambreRepository.save(chambre);
        }
        log.info("reservation supp");
    }



}
