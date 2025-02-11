package it.unisa.HAYT.controllers;

import it.unisa.HAYT.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session, Model model) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user != null) {
            session.invalidate();
        }
        model.addAttribute("hideNavLinks", false);

        return "index";
    }

}
