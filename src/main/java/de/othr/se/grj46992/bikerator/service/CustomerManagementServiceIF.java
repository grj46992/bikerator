package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Address;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerManagementServiceIF {

    public void createCustomer(Customer customer);
    public Iterable<Customer> findAllCustomers();
    public Customer findByUsername(String username);
    public UserDetails loadUserByUsername(String username);
    public void updateCustomerConfigurationList(Customer user, Configuration configuration);
}
