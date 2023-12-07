package com.example.springbootesprit.service;


import com.example.springbootesprit.entities.Feedback;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;

import java.util.List;
import java.util.Map;

public interface IFeedbackServices {
    Feedback ajouterFeedback(Feedback feedback);

    List<Feedback> obtenirFeedbacksParFoyer(Foyer foyer);
    List<Feedback> obtenirFeedbacksParUniversite(Universite universite);
    Map<String, Object> getStatistics();

}
