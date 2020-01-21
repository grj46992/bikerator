package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerManagementServiceIF customerManagementService;

    @RequestMapping(value = "/restapi/customers/emails", method = RequestMethod.POST)
    public List<String> getCustomerEmails() {
        return customerManagementService.readAllCustomerEmails();
    }
}
