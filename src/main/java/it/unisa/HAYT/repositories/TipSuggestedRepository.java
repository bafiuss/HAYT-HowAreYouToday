package it.unisa.HAYT.repositories;

import it.unisa.HAYT.dto.TipCountDTO;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipSuggestedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipSuggestedRepository extends JpaRepository<TipSuggestedEntity, Long> {
    Optional<TipSuggestedEntity> findTopByPatientOrderByIdDesc(PatientEntity patient);

    @Query("""
        SELECT t.id AS tipId, COUNT(ts.id) AS count
        FROM TipSuggestedEntity ts
        JOIN ts.tip t
        WHERE ts.completed = true AND ts.patient.id = :patientId
        GROUP BY t.id
        """)
    List<TipCountDTO> countCompletedByTipAndPatient(@Param("patientId") Long patientId);

    @Query("""
        SELECT t.id AS tipId, COUNT(ts.id) AS count
        FROM TipSuggestedEntity ts
        JOIN ts.tip t
        WHERE ts.patient.id = :patientId
        GROUP BY t.id
        """)
    List<TipCountDTO> countSuggestedByTipAndPatient(@Param("patientId") Long patientId);
}

