package it.unisa.HAYT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireDTO {
    private int depressedMood;
    private int lossOfInterest;
    private int appetiteChanges;
    private int insomnia;
    private int psychomotorAgitation;
    private int fatigue;
    private int worthlessness;
    private int difficultyConcentratingDepression;

    private int excessiveWorry;
    private int difficultyControllingWorry;
    private int restlessness;
    private int easyFatigue;
    private int difficultyConcentratingAnxiety;
    private int irritability;
    private int muscleTension;
    private int sleepDisturbances;

    private int impactWorkStudy;
    private int impactSocial;
    private int impactLeisure;
}
