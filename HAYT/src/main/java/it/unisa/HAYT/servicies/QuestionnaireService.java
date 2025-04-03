package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.QuestionnaireDTO;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.QuestionnaireEntity;
import it.unisa.HAYT.repositories.QuestionnaireRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public void saveQuestionnaire(QuestionnaireDTO questionnaireDTO, PatientEntity patient) {

        QuestionnaireEntity questionnaire = new QuestionnaireEntity();

        questionnaire.setDepressedMood(questionnaireDTO.getDepressedMood());
        questionnaire.setLossOfInterest(questionnaireDTO.getLossOfInterest());
        questionnaire.setAppetiteChanges(questionnaireDTO.getAppetiteChanges());
        questionnaire.setInsomnia(questionnaireDTO.getInsomnia());
        questionnaire.setPsychomotorAgitation(questionnaireDTO.getPsychomotorAgitation());
        questionnaire.setFatigue(questionnaireDTO.getFatigue());
        questionnaire.setWorthlessness(questionnaireDTO.getWorthlessness());
        questionnaire.setDifficultyConcentratingDepression(questionnaireDTO.getDifficultyConcentratingDepression());

        questionnaire.setExcessiveWorry(questionnaireDTO.getExcessiveWorry());
        questionnaire.setDifficultyControllingWorry(questionnaireDTO.getDifficultyControllingWorry());
        questionnaire.setRestlessness(questionnaireDTO.getRestlessness());
        questionnaire.setEasyFatigue(questionnaireDTO.getEasyFatigue());
        questionnaire.setDifficultyConcentratingAnxiety(questionnaireDTO.getDifficultyConcentratingAnxiety());
        questionnaire.setIrritability(questionnaireDTO.getIrritability());
        questionnaire.setMuscleTension(questionnaireDTO.getMuscleTension());
        questionnaire.setSleepDisturbances(questionnaireDTO.getSleepDisturbances());

        questionnaire.setPatient(patient);

        questionnaire.setCompletedAt(LocalDateTime.now());

        questionnaireRepository.save(questionnaire);
    }

    public LocalDateTime getLastQuestionnaireDate(PatientEntity patient) {
        return questionnaireRepository.findLastSubmissionDateByPatientId(patient.getId());
    }

    public long getSecondsUntilNextQuestionnaire(PatientEntity patient) {
        LocalDateTime lastSubmission = getLastQuestionnaireDate(patient);
        if (lastSubmission == null) return 0;

        LocalDateTime nextAvailable = lastSubmission.plusDays(7);
        Duration timeLeft = Duration.between(LocalDateTime.now(), nextAvailable);

        return Math.max(timeLeft.getSeconds(), 0);
    }

}

