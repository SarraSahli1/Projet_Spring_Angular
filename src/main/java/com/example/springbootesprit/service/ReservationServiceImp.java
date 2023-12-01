package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Etudiant;
import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.repositories.ChambreRepository;
import com.example.springbootesprit.repositories.EtudiantRepository;
import com.example.springbootesprit.repositories.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationServiceImp implements IReservationService{
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ChambreRepository chambreRepository;
    @Autowired
    EtudiantRepository etudiantRepository;

public String affecterReservationAChambre(String idReservation, long idChambre) {
    try {
        Optional<Chambre> optionalChambre = chambreRepository.findById(idChambre);
        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (optionalChambre.isPresent() && optionalReservation.isPresent()) {
            Chambre chambre = optionalChambre.get();
            Reservation reservation = optionalReservation.get();

            reservation.setChambre(chambre);
            reservationRepository.save(reservation);

            return "Reservation successfully assigned to chambre.";
        } else {
            return "Chambre or Reservation not found.";
        }
    } catch (Exception e) {
        return "Error occurred: " + e.getMessage();
    }
}

  /**  public Chambre desaffecterReservationChambre(long idChambre) {
        Chambre chambre = chambreRepository.findById(idChambre).orElse(null);

        if (chambre != null && chambre.getReservations() != null) {
            chambre.getReservations().clear(); // Supprime toutes les réservations
            return chambreRepository.save(chambre);
        } else {
            return chambre; // Renvoie la chambre sans modification si elle n'a pas de réservations
        }
    }**/
  public String desaffecterReservationDeChambre(String idReservation) {
      try {
          Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

          if (optionalReservation.isPresent()) {
              Reservation reservation = optionalReservation.get();
              reservation.setChambre(null); // Dissocier la réservation de la chambre en définissant la chambre à null
              reservationRepository.save(reservation);
              return "Reservation successfully removed from chambre.";
          } else {
              return "Reservation not found.";
          }
      } catch (Exception e) {
          return "Error occurred: " + e.getMessage();
      }
  }




    public String affecterReservationAEtudiant(String idReservation, long idEtudiant) {
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);
            Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(idEtudiant);

            if (optionalEtudiant.isPresent() && optionalReservation.isPresent()) {
                Etudiant etudiant = optionalEtudiant.get();
                Reservation reservation = optionalReservation.get();

                etudiant.getReservations().add(reservation);
                reservation.getEtudiants().add(etudiant);

                etudiantRepository.save(etudiant);
                reservationRepository.save(reservation);

                return "Reservation successfully assigned to etudiant.";
            } else {
                return "Etudiant or Reservation not found.";
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        reservationRepository.deleteById(id);

    }

    @Override
    public Reservation update(Reservation reservation) {
        // Retrieve the existing reservation from the repository
        Reservation existingReservation = reservationRepository.findById(reservation.getIdReservation())
                .orElseThrow(() -> new EntityNotFoundException("No Reservation with id " + reservation.getIdReservation() + " was found!"));

        // Check if the existing reservation is not null
        if (existingReservation != null) {
            // Update the properties of the existing reservation with the new values
            existingReservation.setAnneeUniversite(reservation.getAnneeUniversite());
            existingReservation.setEstValide(reservation.isEstValide());
            existingReservation.setCommentaire(reservation.getCommentaire());
            // Set other properties accordingly

            // Save the updated reservation back to the repository
            reservationRepository.save(existingReservation);

            // Return the updated reservation
            return existingReservation;
        }

        // Return null or handle appropriately if the existing reservation is null
        return null;
    }

    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }





}
