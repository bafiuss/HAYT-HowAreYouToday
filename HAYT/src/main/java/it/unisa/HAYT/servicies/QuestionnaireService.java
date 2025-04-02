package it.unisa.HAYT.servicies;

import it.unisa.HAYT.dto.QuestionnaireDTO;
import it.unisa.HAYT.entities.QuestionnaireEntity;
import it.unisa.HAYT.repositories.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public void saveQuestionnaire(QuestionnaireDTO questionnaireDTO) {
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

        questionnaireRepository.save(questionnaire);
    }
}

