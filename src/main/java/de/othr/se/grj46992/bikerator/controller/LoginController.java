package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.service.CustomerServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private CustomerServiceIF customerService;

    @RequestMapping("/loginCustomer")
    public String readCustomer(
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        Customer user = customerService.readCustomer(username, password.hashCode());
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("customerId", user.getCustId());
            return "loginSuccess";
        }
        else {
            model.addAttribute("status", "Anmeldung fehlgeschlagen.");
            return "login";
        }



    }
}
