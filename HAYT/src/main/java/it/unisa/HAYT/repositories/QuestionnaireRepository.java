package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.QuestionnaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity, Long> {
    @Query("SELECT q.completedAt FROM QuestionnaireEntity q WHERE q.patient.id = :patientId ORDER BY q.completedAt DESC LIMIT 1")
    LocalDateTime findLastSubmissionDateByPatientId(Long patientId);

    @Query("SELECT q FROM QuestionnaireEntity q WHERE q.patient.id =:patientId")
    List<QuestionnaireEntity> findPatientQuestionnaires(Long patientId);
}
