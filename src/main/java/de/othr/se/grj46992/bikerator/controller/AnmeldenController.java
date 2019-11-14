package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Kunde;
import de.othr.se.grj46992.bikerator.service.KundeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnmeldenController {

    @Autowired
    private KundeServiceIF kundeService;

    @RequestMapping("/kundeAnmelden")
    public String kundeAnmelden(
            @ModelAttribute("username") String username,
            @ModelAttribute("passwort") String passwort,
            Model model
    ) {
        Kunde benutzer = kundeService.kundeAnmelden(username, passwort.hashCode());
        if (benutzer != null) {
            model.addAttribute("username", benutzer.getUsername());
            model.addAttribute("kundId", benutzer.getKundId());
            return "anmeldungErfolgreich";
        }
        else {
            model.addAttribute("status", "Anmeldung fehlgeschlagen.");
            return "anmelden";
        }



    }
}
