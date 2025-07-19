package it.unisa.HAYT.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SentimentDTO {

    private String label;
    private double score;

    public String getReadableLabel() {
        return switch (label) {
            case "LABEL_0" -> "NEGATIVE";
            case "LABEL_1" -> "NEUTRAL";
            case "LABEL_2" -> "POSITIVE";
            default -> "UNKNOWN";
        };
    }
}

