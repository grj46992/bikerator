package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.service.CustomerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerManagementService customerManagementService;

    @RequestMapping("/restapi/customers/emailList")
    public List<String> getCustomerEmails() {
        return customerManagementService.readCustomerEmailList();
    }
}
