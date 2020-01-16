package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Order;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CustomerManagementServiceIF {

    public void createCustomer(Customer customer);
    public void  updateCustomer(Customer customer);
    public void  deleteCustomer(Customer customer);
    public Iterable<Customer> readAllCustomers();
    public Customer readByUsername(String username);
    public UserDetails loadUserByUsername(String username);
    public void updateCustomerConfigurationList(Customer user, Configuration configuration);
    public void createPost(String title, String text, String email, String password, String picture);
    public void updateCompletedOrderList(Customer customer, Order order);
    public void deleteCurrentOrder(Customer customer);
    public List<String> readCustomerEmailList();
}
