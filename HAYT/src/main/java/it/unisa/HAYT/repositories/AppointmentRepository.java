package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByPatientIdAndPsychotherapistId(Long patientId, Long psychotherapistId);
    List<AppointmentEntity> findByPsychotherapistId(Long psychotherapistId);
}


