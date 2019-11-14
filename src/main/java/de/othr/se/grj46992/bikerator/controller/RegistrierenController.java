package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Kunde;
import de.othr.se.grj46992.bikerator.service.KundeServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrierenController {

    @Autowired
    private KundeServiceIF kundeService;

    @RequestMapping("/kundeAnlegen")
    public String kundeAnlegen(
            @ModelAttribute("vorname") String vorname,
            @ModelAttribute("nachname") String nachname,
            @ModelAttribute("username") String username,
            @ModelAttribute("passwort") String passwort,
            Model model
    ) {
        Kunde kunde = new Kunde();
        kunde.setVorname(vorname);
        kunde.setNachname(nachname);
        kunde.setUsername(username);
        kunde.setPasswort(passwort.hashCode());
        kunde = kundeService.kundeAnlegen(kunde);
        model.addAttribute("username", kunde.getUsername());
        model.addAttribute("kundId", kunde.getKundId());
        model.addAttribute("vorname", kunde.getVorname());
        model.addAttribute("nachname", kunde.getNachname());
        model.addAttribute("passwort", kunde.getPasswort());

        return "kundenkontoAngelegt";
    }
}
