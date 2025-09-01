package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.*;
import it.unisa.HAYT.entities.TipEntity.Type;
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

        model.addAttribute("breathingTips", tipService.getTipsByType(Type.valueOf("breathingExercise")));
        model.addAttribute("muscleTips", tipService.getTipsByType(Type.valueOf("muscleRelaxation")));
        model.addAttribute("mindfulnessTips", tipService.getTipsByType(Type.valueOf("mindfulnessMeditation")));

        model.addAttribute("favoriteIds", tipService.getFavoriteTipIds(patient));
        model.addAttribute("suggestedTip", tipService.getSuggestionIfLastEntryNegative(patient).orElse(null));

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

    @PostMapping("/patient-dashboard/tips/complete/{id}")
    public String markTipAsComplete(@PathVariable Long id, HttpSession session) {
        tipService.markAsComplete(id);
        return "redirect:/patient-dashboard/tips";
    }


}
