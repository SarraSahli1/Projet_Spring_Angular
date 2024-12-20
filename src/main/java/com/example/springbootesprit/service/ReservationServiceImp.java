package com.example.springbootesprit.service;

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

    public String affecterReservationAChambre(Long idReservation, long idChambre) {
        try {
            Optional<Chambre> optionalChambre = chambreRepository.findById(idChambre);
            Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

            if (optionalChambre.isPresent() && optionalReservation.isPresent()) {
                Chambre chambre = optionalChambre.get();
                Reservation reservation = optionalReservation.get();

                // Add the reservation to the set of reservations in the Chambre entity
                Set<Reservation> reservations = chambre.getReservations();
                reservations.add(reservation);

                // Update the Chambre entity with the modified set of reservations
                chambre.setReservations(reservations);

                // Save the updated Chambre entity
                chambreRepository.save(chambre);

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
    public String desaffecterReservationDeChambre(Long idReservation) {
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

            if (optionalReservation.isPresent()) {
                Reservation reservation = optionalReservation.get();

                // Retrieve the associated Chambre
                Chambre chambre = findChambreForReservation(reservation);

                // Remove the reservation from the set of reservations in the Chambre entity
                if (chambre != null) {
                    chambre.getReservations().remove(reservation);
                    // Update the Chambre entity with the modified set of reservations
                    chambreRepository.save(chambre);
                }

                // Dissociate the reservation from the chambre
                // Note: Assuming you don't have a direct reference to Chambre in Reservation
                // If you do, you can directly use reservation.setChambre(null)
                reservationRepository.save(reservation);

                return "Reservation successfully removed from chambre.";
            } else {
                return "Reservation not found.";
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    private Chambre findChambreForReservation(Reservation reservation) {
        List<Chambre> allChambres = chambreRepository.findAll();

        for (Chambre chambre : allChambres) {
            if (chambre.getReservations().contains(reservation)) {
                return chambre;
            }
        }

        return null;  // If no associated Chambre is found
    }



    public String affecterReservationAEtudiant(Long idReservation, long idEtudiant) {
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);
            Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(idEtudiant);

            if (optionalEtudiant.isPresent() && optionalReservation.isPresent()) {
                Etudiant etudiant = optionalEtudiant.get();
                Reservation reservation = optionalReservation.get();

                // Check if the student has made a previous reservation
                boolean hasPreviousReservation = etudiant.getReservations().stream()
                        .anyMatch(prevReservation -> prevReservation.getIdReservation() == idReservation);

                if (!hasPreviousReservation) {
                    etudiant.getReservations().add(reservation);
                    reservation.getEtudiants().add(etudiant);

                    etudiantRepository.save(etudiant);
                    reservationRepository.save(reservation);

                    return "Reservation successfully assigned to etudiant.";
                } else {
                    return "The student has already made a reservation.";
                }
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

    public Reservation getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation).get();
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);

    }

    @Override
    public Reservation update(Reservation reservation) {
        Long id = reservation.getIdReservation();
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Reservation with id " + id + " was found!"));
        // Update existingReservation with the values from the provided reservation
        existingReservation.setAnneeUniversite(reservation.getAnneeUniversite());
        existingReservation.setEstValide(reservation.isEstValide());
        existingReservation.setCommentaire(reservation.getCommentaire());
        // Add other property updates...

        reservationRepository.save(existingReservation);

        return existingReservation;
    }
    @Override
    public String getPercentageOfStudentsWithReservation() {
        long totalStudents = etudiantRepository.count();
        long studentsWithReservation = etudiantRepository.countDistinctByReservationsIsNotNull();

        if (totalStudents > 0) {
            double percentage = ((double) studentsWithReservation / totalStudents) * 100;
            return "Le pourcentage des étudiants qui ont fait une réservation : " + percentage + "%";
        } else {
            return "Aucun étudiant trouvé.";
        }
    }





    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }






}