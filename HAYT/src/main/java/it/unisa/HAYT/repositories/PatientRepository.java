package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.PatientEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE PatientEntity p SET p.psychotherapist.id = :psychotherapistId WHERE p.id = :patientId")
    int updatePsychotherapist(@Param("patientId") Long patientId, @Param("psychotherapistId") Long psychotherapistId);

}