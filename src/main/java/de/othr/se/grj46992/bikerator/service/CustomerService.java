package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CustomerServiceIF {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer;
    }

    @Override
    public Customer readCustomer(String username, int passwort) {
        Customer user = customerRepository.findByUsernameAndPassword(username, passwort);
        return user;
    }
}