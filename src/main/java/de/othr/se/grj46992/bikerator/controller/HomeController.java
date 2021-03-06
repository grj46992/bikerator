package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;

    @RequestMapping(value = "/")
    public String start() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(required=false) String error,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", true);
            return "login";
        }
        return "login";
    }

    @RequestMapping(value = "/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping(value = "/overview")
    public String overview(
            Model model
    ) {
        model.addAttribute("categories", articleManagementService.readFatherCategories());
        return "overview";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
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
            @ModelAttribute("passwordCheck") String passwordCheck,
            @ModelAttribute("email") String email,
            Model model
    ) {

        Customer customer = new Customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setId(username);
        customer.setPassword(password);
        customer.setEmail(email);
        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setPostcode(postcode);
        address.setTown(town);
        address.setCountry(country);
        customer.setAddress(address);
        // Create new customer if username and email is not already used
        int status = customerManagementService.createCustomer(customer);
        // Load template according to status of registration
        switch (status) {
            case 0:
                model.addAttribute("nameError", true);
                return "signup";

            case 1:
                model.addAttribute("emailError", true);
                return "signup";

            case 2:
                return "login";

            default:
                return "login";
        }
    }
}
