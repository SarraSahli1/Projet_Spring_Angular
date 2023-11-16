package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre,Long> {}

