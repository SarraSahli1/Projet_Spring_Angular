package com.example.springbootesprit.controller;

import com.example.springbootesprit.entities.Foyer;
import com.example.springbootesprit.entities.TransactionPaiement;
import com.example.springbootesprit.entities.Universite;
import com.example.springbootesprit.repositories.TransactionPaiementRepository;
import com.example.springbootesprit.service.FoyerServiceImp;
import com.example.springbootesprit.service.PaimentSevices;
import com.example.springbootesprit.service.UniversiteServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http:localhost:4200")
@RestController
@RequestMapping("/paiement")
@AllArgsConstructor
public class PaimentController {

    @Autowired
    private PaimentSevices paimentSevices ;
    private TransactionPaiementRepository transactionPaiementRepository;

    private final FoyerServiceImp foyerServices;
    private final UniversiteServiceImp universiteServices;



    @PostMapping("/effectuer")
    public ResponseEntity<?> effectuerPaiement(@RequestBody PaiementRequest paiementRequest) {
        try {
            // Call the method in your service to perform the payment logic
            TransactionPaiement transactionEntity = paimentSevices.effectuerPaiement(paiementRequest);

            // Return the transactionEntity in the ResponseEntity
            return new ResponseEntity<>(transactionEntity, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions appropriately and return an error response
            return new ResponseEntity<>("Error processing payment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
    @PostMapping("/effectuer")
    public ResponseEntity<?> effectuerPaiement(@RequestBody PaiementRequest paiementRequest) {
        try {
            Foyer foyer = paiementRequest.getFoyer();
            Universite universite = paiementRequest.getUniversite();

            // Check if foyer and universite are not null
            if (foyer != null && universite != null) {
                // Initialize the montant to BigDecimal.ZERO to avoid NullPointerException
                BigDecimal montant = BigDecimal.ZERO;

                // Synchronize payment amounts
                BigDecimal montantPaiementFoyer = foyer.getMontantPaiement() != null ? foyer.getMontantPaiement() : BigDecimal.ZERO;
                BigDecimal montantPaiementUniversite = universite.getMontantPaiement() != null ? universite.getMontantPaiement() : BigDecimal.ZERO;

                // Calculate the total amount
                BigDecimal montantTotal = montantPaiementFoyer.add(montantPaiementUniversite);

                // Create the transaction only if the total amount is greater than zero
                if (montantTotal.compareTo(BigDecimal.ZERO) > 0) {
                    // Proceed with the payment
                    TransactionPaiement nouvelleTransaction = paimentSevices.effectuerPaiement(foyer, universite, montantTotal);
                    return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleTransaction);
                } else {
                    return ResponseEntity.badRequest().body("Le montant total du paiement doit être supérieur à zéro.");
                }
            } else {
                return ResponseEntity.badRequest().body("Foyer or Universite is null.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the details of the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }
*/
    @GetMapping("/paiements")
    public ResponseEntity<Map<String, Object>> getPaiements() {

        // Retrieve the list of payments
        List<TransactionPaiement> paiements = transactionPaiementRepository.findAll();

        // Identify payments with null foyer or universite
        List<TransactionPaiement> paymentsWithNullFoyerOrUniversite = new ArrayList<>();
        for (TransactionPaiement paiement : paiements) {
            if (paiement.getFoyer() == null || paiement.getUniversite() == null) {
                paymentsWithNullFoyerOrUniversite.add(paiement);
            }
        }

        // Handle payments with null values
        // (e.g., update or discard them)

        // Process the remaining payments (those with valid foyer and universite)
        List<TransactionPaiement> filteredPaiements = paiements.stream()
                .filter(paiement -> paiement.getFoyer() != null && paiement.getUniversite() != null)
                .collect(Collectors.toList());

        // Create the report using the filtered payments
        Map<String, Object> rapport = new HashMap<>();
        rapport.put("totalPaiements", filteredPaiements.size());

        // Calculate statistics by foyer
        Map<Foyer, Long> paiementsParFoyer = filteredPaiements.stream()
                .collect(Collectors.groupingBy(TransactionPaiement::getFoyer, Collectors.counting()));
        rapport.put("paiementsParFoyer", paiementsParFoyer);

        // Calculate statistics by university
        Map<Universite, Long> paiementsParUniversite = filteredPaiements.stream()
                .collect(Collectors.groupingBy(TransactionPaiement::getUniversite, Collectors.counting()));
        rapport.put("paiementsParUniversite", paiementsParUniversite);

        // Calculate statistics by payment type
        Map<String, Long> paiementsParType = filteredPaiements.stream()
                .collect(Collectors.groupingBy(TransactionPaiement::getTypePaiement, Collectors.counting()));
        rapport.put("paiementsParType", paiementsParType);

        return ResponseEntity.ok(rapport);
    }
}