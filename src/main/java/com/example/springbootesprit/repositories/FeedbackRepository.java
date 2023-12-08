package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Feedback;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
List<Feedback>findByFoyer(Foyer foyer);
List<Feedback>findByUniversite(Universite universite);
}
