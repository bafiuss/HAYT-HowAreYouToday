package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.TipEntity;
import it.unisa.HAYT.servicies.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientTipsController {

    @Autowired
    private TipService tipService;

    @GetMapping("/patient-dashboard/tips")
    public String showPatientTipsPage(Model model){

        model.addAttribute("hideNavLinks", false);
        model.addAttribute("breathingTips", tipService.getTipsByType("breathing_exercise"));
        model.addAttribute("muscleTips", tipService.getTipsByType("muscle_relaxation"));
        model.addAttribute("mindfulnessTips", tipService.getTipsByType("mindfulness_meditation"));


        return "tips";
    }
}
