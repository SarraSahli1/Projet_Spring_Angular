package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.Chambre;
import com.example.springbootesprit.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre,Long> {
    List<Chambre> findByBloc(Bloc bloc);
    List<Chambre> findByTypeC(TypeChambre typeChambre);
    List<Chambre> findByReservationsAnneeUniversiteBeforeAndReservationsIsNotNull(Date dateLimite);

}
