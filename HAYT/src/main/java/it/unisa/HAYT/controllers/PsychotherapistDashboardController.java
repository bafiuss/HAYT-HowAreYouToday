package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PsychotherapistDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/psychotherapist-dashboard")
    public String showPsychotherapistDashboardPage(Model model, HttpSession session) {
        UserEntity psychotherapist = (UserEntity) session.getAttribute("user");
        int numberOfPatients = userService.getNumberOfPatientsAssociated(psychotherapist.getId());

        List<PatientEntity> firstTwoPatients = userService.getFirstTwoUsers();

        model.addAttribute("numberOfPatient", numberOfPatients);
        model.addAttribute("firstTwoPatients", firstTwoPatients);
        model.addAttribute("hideNavLinks", false);
        model.addAttribute("currentPage", "psychotherapist-dashboard");

        return "psychotherapist-dashboard";
    }


}
