package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Feedback;
import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.FeedbackRepository;
import com.example.springbootesprit.service.FeedbackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http:localhost:4200")
@RestController
@RequestMapping
@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackServices feedbackServices;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Feedback> ajouterFeedback(@RequestBody Feedback feedback){
        Feedback nouveauFeedback = feedbackServices.ajouterFeedback(feedback);
        return new ResponseEntity<>(nouveauFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {

        // Retrieve the list of feedback objects
        List<Feedback> feedbackList = feedbackRepository.findAll();

        // Calculate the statistics
        long totalFeedback = feedbackList.stream().count();
        Map<String, Long> feedbackByCommentaire = feedbackList.stream()
                .collect(Collectors.groupingBy(Feedback::getCommentaire, Collectors.counting()));

        // Retrieve the actual IDs of the universities
        Map<Long, Universite> universites = feedbackList.stream()
                .map(Feedback::getUniversite)
                .distinct()
                .collect(Collectors.toMap(Universite::getIdUniversite, universite -> universite));

        // Retrieve the actual IDs of the foyers
        Map<Long, Foyer> foyers = feedbackList.stream()
                .map(Feedback::getFoyer)
                .distinct()
                .collect(Collectors.toMap(Foyer::getIdFoyer, foyer -> foyer));

        // Map feedback objects to their respective university IDs
        Map<Long, Long> feedbackByUniversite = feedbackList.stream()
                .collect(Collectors.groupingBy(feedback -> universites.get(feedback.getUniversite().getIdUniversite()).getIdUniversite(), Collectors.counting()));

        // Map feedback objects to their respective foyer IDs
        Map<Long, Long> feedbackByFoyer = feedbackList.stream()
                .collect(Collectors.groupingBy(feedback -> foyers.get(feedback.getFoyer().getIdFoyer()).getIdFoyer(), Collectors.counting()));

        // Create the response
        Map<String, Object> response = new HashMap<>();
        response.put("totalFeedback", totalFeedback);
        response.put("feedbackByCommentaire", feedbackByCommentaire);
        response.put("feedbackByUniversite", feedbackByUniversite);
        response.put("feedbackByFoyer", feedbackByFoyer);

        return ResponseEntity.ok(response);
    }

}


