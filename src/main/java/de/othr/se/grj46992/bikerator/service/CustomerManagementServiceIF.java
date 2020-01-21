package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

public interface CustomerManagementServiceIF {

    int createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    Iterable<Customer> readAllCustomers();

    List<String> readAllCustomerEmails();

    Customer readById(String username);

    UserDetails loadUserByUsername(String id);

    Address createAddress(Address address);

    void updateCustomerConfigurationList(Customer user, Configuration configuration);

    void createPost(String title, String email);

    void updateCompletedOrderList(Customer customer, Order order);

    void deleteCurrentOrder(Customer customer);
}
