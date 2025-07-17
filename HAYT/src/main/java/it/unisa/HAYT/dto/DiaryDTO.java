package it.unisa.HAYT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO {

    private String title;
    private String content;
    private String mood;

}

