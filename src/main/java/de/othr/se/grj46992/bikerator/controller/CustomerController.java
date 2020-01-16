package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;

    @RequestMapping("/user/account")
    public String account(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        if (currentConfig != null) {
            model.addAttribute("openConfig", currentConfig);
        }
        Iterable<Customer> list = customerManagementService.readAllCustomers();
        model.addAttribute("customers", list);
        return "user/account";
    }

    @RequestMapping("/user/editAccount")
    public String editCustomer(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.readByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/editAccount";
    }

    @RequestMapping("/user/editAccount/save")
    public String saveCustomer(
            HttpSession session,
            Principal principal,
            @ModelAttribute("firstname") String firstname,
            @ModelAttribute("lastname") String lastname,
            @ModelAttribute("street") String street,
            @ModelAttribute("number") String number,
            @ModelAttribute("postcode") String postcode,
            @ModelAttribute("town") String town,
            @ModelAttribute("country") String country,
            @ModelAttribute("password") String password,
            @ModelAttribute("email") String email,
            Model model
    ) {
        Customer customer = customerManagementService.readByUsername(principal.getName());
        Address customerAddress = customer.getAddress();

        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setPassword(password);
        customer.setEmail(email);
        customerAddress.setStreet(street);
        customerAddress.setNumber(number);
        customerAddress.setPostcode(postcode);
        customerAddress.setTown(town);
        customerAddress.setCountry(country);
        customer.setAddress(customerAddress);
        customerManagementService.updateCustomer(customer);

        model.addAttribute("saved", true);
        return "user/account";
    }

    @RequestMapping("/user/editAccount/delete")
    public String deleteCustomer(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readByUsername(principal.getName());
        customerManagementService.deleteCustomer(customer);
        session.invalidate();
        return "redirect:/logout";
    }
}
