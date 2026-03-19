package it.unisa.HAYT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticContentController {

    @GetMapping({"/","/index"})
    public String showHomePage(Model model) {
        model.addAttribute("hideNavLinks",false);
        return "index";
    }

    @GetMapping("/features")
    public String showFeaturesPage(Model model) {
        model.addAttribute("hideNavLinks",false);
        model.addAttribute("currentPage","features");
        return "features";
    }

    @GetMapping("/mental-health-disorders")
    public String showMentalHealthDisordersPage(Model model) {
        model.addAttribute("hideNavLinks",false);
        model.addAttribute("currentPage","mental-health-disorders");
        return "mental-health-disorders";
    }
}