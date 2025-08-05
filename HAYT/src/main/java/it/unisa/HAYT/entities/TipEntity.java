package it.unisa.HAYT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tips")
public class TipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String title;

    @Column(length = 512)
    private String step1;
    @Column(length = 512)
    private String step2;
    @Column(length = 512)
    private String step3;
    @Column(length = 512)
    private String step4;
    @Column(length = 512)
    private String step5;

    public TipEntity(String type, String title, String step1, String step2, String step3, String step4, String step5){
        this.type = type;
        this.title = title;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
        this.step5 = step5;
    }
}
