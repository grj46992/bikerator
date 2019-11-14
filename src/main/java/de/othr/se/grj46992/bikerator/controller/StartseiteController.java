package de.othr.se.grj46992.bikerator.controller;


import de.othr.se.grj46992.bikerator.entity.Kunde;
import de.othr.se.grj46992.bikerator.service.KundeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartseiteController {

    @Autowired
    private KundeServiceIF kundeService;

    @RequestMapping("/")
    public String starten() {
        return "index";
    }

    @RequestMapping("/registrieren")
    public String registrieren() {
        return "registrieren";
    }

    @RequestMapping("/anmelden")
    public String anmelden() {
        return "anmelden";
    }
}
