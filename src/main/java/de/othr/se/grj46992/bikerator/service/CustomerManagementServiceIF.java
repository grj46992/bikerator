package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CustomerManagementServiceIF {

    void createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    Iterable<Customer> readAllCustomers();

    Customer readById(String username);

    UserDetails loadUserByUsername(String id);

    void updateCustomerConfigurationList(Customer user, Configuration configuration);

    void createPost(String title, String text, String email, String password, String picture);

    void updateCompletedOrderList(Customer customer, Order order);

    void deleteCurrentOrder(Customer customer);

    List<String> readCustomerEmailList();
}
