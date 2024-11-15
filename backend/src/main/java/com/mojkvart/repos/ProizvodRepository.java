package com.mojkvart.repos;

import com.mojkvart.domain.Proizvod;
import com.mojkvart.domain.Trgovina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {

    Proizvod findFirstByTrgovina(Trgovina trgovina);

    @Query("SELECT p FROM Proizvod p WHERE p.trgovina.trgovinaId = :trgovinaId")
    List<Proizvod> findByTrgovinaId(@Param("trgovinaId") Integer trgovinaId);

    @Query("SELECT p FROM Proizvod p WHERE p.proizvodFlag = true")
    List<Proizvod> findAllApproved();

    @Query("SELECT p FROM Proizvod p WHERE p.proizvodFlag = false")
    List<Proizvod> findAllNotApproved();

}
