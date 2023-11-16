package com.example.springbootesprit.controller;


import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.service.IReservationService;
import com.example.springbootesprit.service.ReservationServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/Reservation")

public class ReservationController {

    @Autowired
    ReservationServiceImp iReservationService;
    @PostMapping("/addReservation")
    Reservation addReservation(@RequestBody Reservation reservation)
    {
        return iReservationService.addReservation(reservation);
    }
    @GetMapping("/allReservation")
    List<Reservation> allReservations(){
        return iReservationService.getAllReservation();
    }
    @GetMapping("/reservationbyId/{id}")
    Reservation reservationById (@PathVariable  String id)
    {
        return iReservationService.getReservationById(id);
    }

    @PutMapping("/updateRes/{id}")
    Reservation updateRes(@RequestBody Reservation reservation)
    {
        return iReservationService.update(reservation);
    }
    @PutMapping("/updateReservation/{id}")
    Reservation updateRes(@RequestBody Reservation reservation, @PathVariable String id)
    {
        reservation.setIdReservation(id);
        return iReservationService.update(reservation);
    }
    @DeleteMapping("/deleteRes/{id}")
    void deleteRes(@PathVariable String id)
    {
        iReservationService.delete(id);
    }
    @PutMapping("/affecterReservationAChambre/{idReservation}/{idChambre}")
    String affecterReservationAChambre(@PathVariable String idReservation, @PathVariable long idChambre)
    {
        return iReservationService.affecterReservationAChambre(idReservation,idChambre);
    }
    @PutMapping("/affecterReservationAEtudiant/{idReservation}/{idEtudiant}")
    String affecterReservationAEtudiant(@PathVariable String idReservation, @PathVariable long idEtudiant)
    {
        return iReservationService.affecterReservationAEtudiant(idReservation,idEtudiant);
    }

    @PutMapping("/desaffecterReservationAChambre/{idReservation}")
    String desaffecterReservationAChambre(@PathVariable String idReservation)
    {
        return iReservationService.desaffecterReservationDeChambre(idReservation);
    }




}
