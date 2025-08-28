package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.TipEntity;
import it.unisa.HAYT.entities.TipFavoriteEntity;
import it.unisa.HAYT.entities.TipSuggestedEntity;
import it.unisa.HAYT.repositories.TipFavoriteRepository;
import it.unisa.HAYT.repositories.TipSuggestedRepository;
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



    @GetMapping("/patient-dashboard/tips")
    public String showPatientTipsPage(Model model, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");

        model.addAttribute("breathingTips", tipService.getTipsByType("breathing_exercise"));
        model.addAttribute("muscleTips", tipService.getTipsByType("muscle_relaxation"));
        model.addAttribute("mindfulnessTips", tipService.getTipsByType("mindfulness_meditation"));

        model.addAttribute("favoriteIds", tipService.getFavoriteTipIds(patient));

        model.addAttribute("hideNavLinks", false);

        return "tips";
    }

    @PostMapping("/patient-dashboard/tips/add-favorite/{id}")
    public String addFavorite(@PathVariable Long id, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        tipService.addFavorite(patient, id);
        return "redirect:/patient-dashboard/tips";
    }

    @PostMapping("/patient-dashboard/tips/remove-favorite/{id}")
    public String removeFavorite(@PathVariable Long id, HttpSession session) {
        PatientEntity patient = (PatientEntity) session.getAttribute("user");
        tipService.removeFavorite(patient, id);
        return "redirect:/patient-dashboard/tips";
    }


}
