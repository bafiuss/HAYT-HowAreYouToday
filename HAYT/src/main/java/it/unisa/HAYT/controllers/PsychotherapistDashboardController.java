package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PsychotherapistDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/psychotherapist-dashboard")
    public String showPsychotherapistDashboardPage(Model model, HttpSession session) {
        UserEntity psychotherapist = (UserEntity) session.getAttribute("user");
        int numberOfPatients = userService.getNumberOfPatientsAssociated(psychotherapist.getId());

        model.addAttribute("numberOfPatient",numberOfPatients);
        model.addAttribute("hideNavLinks", false);
        model.addAttribute("currentPage", "psychotherapist-dashboard");

        return "psychotherapist-dashboard";
    }


}
