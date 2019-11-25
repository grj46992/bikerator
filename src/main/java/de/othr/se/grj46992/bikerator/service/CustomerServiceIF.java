package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Customer;

public interface CustomerServiceIF {

    public Customer createCustomer(Customer customer);
    public Customer readCustomer(String username, int password);
}
