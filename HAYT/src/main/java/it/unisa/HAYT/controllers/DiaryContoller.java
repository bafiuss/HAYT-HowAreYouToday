package it.unisa.HAYT.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DiaryContoller {

    @GetMapping("/patient-dashboard/diary")
    public String showDiaryPage(Model model){
        model.addAttribute("hideNavLinks", false);
        return "diary";
    }
}
