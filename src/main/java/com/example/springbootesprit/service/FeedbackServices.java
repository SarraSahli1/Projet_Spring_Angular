package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Feedback;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedbackServices implements IFeedbackServices{
    @Autowired
    private FeedbackRepository feedbackRepository;


    @Override
    public Feedback ajouterFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> obtenirFeedbacksParFoyer(Foyer foyer) {
        return feedbackRepository.findByFoyer(foyer);
    }

    @Override
    public List<Feedback> obtenirFeedbacksParUniversite(Universite universite) {
        return feedbackRepository.findByUniversite(universite);
    }
    public Map<String, Object> getStatistics() {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        // Calcule les statistiques
        long totalFeedback = feedbackList.stream().count();
        Map<String, Long> feedbackByCommentaire = feedbackList.stream()
                .collect(Collectors.groupingBy(Feedback::getCommentaire, Collectors.counting()));
        Map<Universite, Long> feedbackByUniversite = feedbackList.stream()
                .collect(Collectors.groupingBy(Feedback::getUniversite, Collectors.counting()));
        Map<Foyer, Long> feedbackByFoyer = feedbackList.stream()
                .collect(Collectors.groupingBy(Feedback::getFoyer, Collectors.counting()));

        // Crée la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("totalFeedback", totalFeedback);
        response.put("feedbackByCommentaire", feedbackByCommentaire);
        response.put("feedbackByUniversite", feedbackByUniversite);
        response.put("feedbackByFoyer", feedbackByFoyer);

        return response;
    }

}
