package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Reservation;

import java.util.List;

public interface IReservationService  {
    Reservation addReservation(Reservation reservation);
    Reservation getReservationById(Long idReservation);
    void delete(Long id);
    Reservation update(Reservation reservation);
    List<Reservation> getAllReservation();
    String getPercentageOfStudentsWithReservation();
    //void affecterReservationAChambre(String idReservation, long idChambre);



}