package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.service.CustomerServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    @Autowired
    private CustomerServiceIF customerService;

    @RequestMapping("/createCustomer")
    public String createCustomer(
            @ModelAttribute("firstname") String firstname,
            @ModelAttribute("lastname") String lastname,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        Customer customer = new Customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setUsername(username);
        customer.setPassword(password.hashCode());
        customer = customerService.createCustomer(customer);
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("custId", customer.getCustId());
        model.addAttribute("firstname", customer.getFirstname());
        model.addAttribute("lastname", customer.getLastname());
        model.addAttribute("password", customer.getPassword());

        return "registrationSuccess";
    }
}
