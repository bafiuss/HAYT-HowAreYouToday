package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.DiaryDTO;
import it.unisa.HAYT.dto.DiaryDTO;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.DiaryRepository;
import it.unisa.HAYT.servicies.DiaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.unisa.HAYT.entities.DiaryEntity;

@RestController
@RequestMapping("/api/diary")
public class DiaryEntryController {

    @Autowired
    private DiaryService diaryService;


    @PostMapping
    public ResponseEntity<DiaryEntity> save(@RequestBody DiaryDTO dto, Model model, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        DiaryEntity savedEntry = diaryService.saveEntry(dto, patient);

        model.addAttribute("diaryEntryDTO", new DiaryDTO());

        return ResponseEntity.ok(savedEntry);
    }

}


