package de.othr.se.grj46992.bikerator.controller;


import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.SimpleAttributeSet;

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
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
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
        model.addAttribute("items", articleManagementService.findAllItems());
        return "artikel";
    }
}
