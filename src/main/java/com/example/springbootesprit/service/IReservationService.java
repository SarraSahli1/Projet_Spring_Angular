package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Reservation;

import java.util.List;

public interface IReservationService  {
    Reservation addReservation(Reservation reservation);
    Reservation getReservationById(String id);
    void delete(String id);
    Reservation update(Reservation reservation);
    List<Reservation> getAllReservation();
    //void affecterReservationAChambre(String idReservation, long idChambre);



}
