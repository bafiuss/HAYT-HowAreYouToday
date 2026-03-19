package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.DiaryDTO;
import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.servicies.DiaryService;
import it.unisa.HAYT.servicies.TipService;
import it.unisa.HAYT.entities.DiaryEntity.Sentiment;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/diary")
public class DiaryEntryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private TipService tipService;

    @PostMapping
    public ResponseEntity<DiaryEntity> save(@RequestBody DiaryDTO dto, Model model, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        DiaryEntity savedEntry = diaryService.saveEntry(dto, patient);

        model.addAttribute("diaryEntryDTO", new DiaryDTO());

        if (savedEntry.getSentiment() == Sentiment.negative) {
            tipService.suggestRandomTip(patient);
        }

        return ResponseEntity.ok(savedEntry);
    }


}


