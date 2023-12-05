package com.example.springbootesprit.repositories;

import com.example.springbootesprit.entities.Bloc;
import com.example.springbootesprit.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BlocRepository extends JpaRepository<Bloc,Long> {

    @Query("SELECT COUNT(ch) FROM Bloc b JOIN b.chambres ch WHERE b.idBloc = :idBloc AND ch.typeC = :type")
    long countChambresByTypeAndBlocId(@Param("type") TypeChambre type, @Param("idBloc") long idBloc);


    Bloc findByNomBloc(String nomBloc);

    List<Bloc> findByNomBlocContaining(String recherche);

}
