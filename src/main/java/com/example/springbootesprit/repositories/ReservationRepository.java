package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}