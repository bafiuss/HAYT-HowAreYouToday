package it.unisa.HAYT.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {

    @GetMapping("/access")
    public String access(){
        return "access";
    }


}
