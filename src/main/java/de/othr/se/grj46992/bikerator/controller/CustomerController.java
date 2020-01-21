package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.OrderManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CustomerController {
    @Autowired
    private CustomerManagementServiceIF customerManagementService;

    @Autowired
    private OrderManagementServiceIF orderManagementService;

    @RequestMapping(value = "/user/account")
    public String account(
            HttpSession session,
            Model model
    ) {
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        if (currentConfig != null && !currentConfig.getItemList().isEmpty()) {
            // Add open configuration to model
            model.addAttribute("openConfig", currentConfig);
        }
        return "user/account";
    }

    @RequestMapping(value = "/user/editAccount")
    public String editCustomer(
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.readById(principal.getName());
        model.addAttribute("user", user);
        return "user/editAccount";
    }

    @RequestMapping(value = "/user/editAccount/save", method = RequestMethod.POST)
    public String saveCustomer(
            Principal principal,
            @ModelAttribute("firstname") String firstname,
            @ModelAttribute("lastname") String lastname,
            @ModelAttribute("street") String street,
            @ModelAttribute("number") String number,
            @ModelAttribute("postcode") String postcode,
            @ModelAttribute("town") String town,
            @ModelAttribute("country") String country,
            @ModelAttribute("email") String email,
            Model model
    ) {
        Customer customer = customerManagementService.readById(principal.getName());
        Address customerAddress = customer.getAddress();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
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

    @RequestMapping(value = "/user/editAccount/password")
    public String editPassword() {
        return "user/editPassword";
    }

    @RequestMapping(value = "/user/editAccount/savePassword", method = RequestMethod.POST)
    public String editPassword(
            @RequestParam(required = false, name="passwordNew") String passwordNew,
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readById(principal.getName());
        customer.setPassword(passwordNew);
        customerManagementService.updateCustomer(customer);
        model.addAttribute("saved", true);
        return "user/account";
    }

    @RequestMapping(value = "/user/editAccount/delete")
    public String deleteCustomer(
            HttpSession session,
            Principal principal
    ) {
        // Delete customer, invalidate session and logout customer
        Customer customer = customerManagementService.readById(principal.getName());
        customerManagementService.deleteCustomer(customer);
        session.invalidate();
        return "redirect:/logout";
    }

    @RequestMapping(value = "/user/shoppingcart")
    public String shoppingcart(
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readById(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);
        model.addAttribute("order", currentOrder);
        return "user/shoppingcart";
    }
}
