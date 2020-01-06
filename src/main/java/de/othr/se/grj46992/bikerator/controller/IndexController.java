package de.othr.se.grj46992.bikerator.controller;


import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.apache.catalina.session.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;

@Controller
public class IndexController {

    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;

    @RequestMapping("/")
    public String start() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "index";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping("/account")
    public String account() {
        return "account";
    }

    @RequestMapping("/register")
    public String register(
            @ModelAttribute("firstname") String firstname,
            @ModelAttribute("lastname") String lastname,
            @ModelAttribute("street") String street,
            @ModelAttribute("number") String number,
            @ModelAttribute("postcode") String postcode,
            @ModelAttribute("town") String town,
            @ModelAttribute("country") String country,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            @ModelAttribute("email") String email,
            Model model
    ) {
        Customer customer = new Customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setEmail(email);
        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setPostcode(postcode);
        address.setTown(town);
        address.setCountry(country);
        customer.setAddress(address);
        customerManagementService.createCustomer(customer);
        return "index";
    }

    @RequestMapping("/kategorien")
    public String listCategories(Model model) {
        model.addAttribute("categories", articleManagementService.findAllCategories());
        return "kategorien";
    }

    @RequestMapping("/artikel")
    public String listItems(Model model) {
        model.addAttribute("items", articleManagementService.findItemsByCategory("Fahrradrahmen"));
        return "artikel";
    }
}
