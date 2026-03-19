package it.unisa.HAYT.controllers;

import it.unisa.HAYT.dto.DiaryDTO;
import it.unisa.HAYT.entities.DiaryEntity;
import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.DiaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/patient-dashboard/diary")
    public String showDiaryPage(Model model, HttpSession session){
        UserEntity patient = (PatientEntity) session.getAttribute("user");
        List<DiaryEntity> thoughtsList = diaryService.findByPatientIdOrderByCreatedAtDesc(patient.getId());

        model.addAttribute("diaryEntryDTO", new DiaryDTO());
        model.addAttribute("thoughtsList", thoughtsList);
        model.addAttribute("hideNavLinks",false);

        return "diary";
    }
}
