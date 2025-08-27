package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipEntity;
import it.unisa.HAYT.repositories.TipCompletedRepository;
import it.unisa.HAYT.servicies.TipService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PatientTipsController {

    @Autowired
    private TipService tipService;

    @Autowired
    private TipCompletedRepository completedRepository;



    @GetMapping("/patient-dashboard/tips")
    public String showPatientTipsPage(Model model, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");

        model.addAttribute("breathingTips", tipService.getTipsByType("breathing_exercise"));
        model.addAttribute("muscleTips", tipService.getTipsByType("muscle_relaxation"));
        model.addAttribute("mindfulnessTips", tipService.getTipsByType("mindfulness_meditation"));

        model.addAttribute("completedTipIds", tipService.getCompletedTipIds(patient));
        model.addAttribute("randomTipIds", tipService.getActiveTipsForPatient(patient));
        model.addAttribute("hideNavLinks", false);

        return "tips";
    }

    @PostMapping("/patient-dashboard/tips/complete/{tipId}")
    public String completeTip(@PathVariable Long tipId, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        tipService.markAsCompleted(patient,tipId);
        return "redirect:/patient-dashboard/tips";
    }

}
