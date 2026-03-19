package it.unisa.HAYT.repositories;

import it.unisa.HAYT.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByPatientIdAndPsychotherapistId(Long patientId, Long psychotherapistId);

    List<AppointmentEntity> findByPsychotherapistId(Long psychotherapistId);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.dateTime < :currentDateTime")
    int countPastAppointments(@Param("currentDateTime") LocalDateTime currentDateTime);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.dateTime > :currentDateTime")
    int countFutureAppointments(@Param("currentDateTime") LocalDateTime currentDateTime);

}


