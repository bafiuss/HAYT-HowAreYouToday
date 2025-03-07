package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.PatientEntity;
import it.unisa.HAYT.entities.UserEntity;
import it.unisa.HAYT.repositories.PatientRepository;
import it.unisa.HAYT.servicies.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientListController {

    @Autowired
    private UserService userService;

    @GetMapping("/patients")
    public String showPatientListPage(Model model, HttpSession session){
        UserEntity psychotherapist = (UserEntity) session.getAttribute("user");
        List<PatientEntity> patients = userService.getPatientsAssociated(psychotherapist.getId());

        model.addAttribute("patientList",patients);
        model.addAttribute("hideNavLinks",false);

        return "patients";
    }

}
