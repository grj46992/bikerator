package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;

public interface OrderManagementServiceIF {

    public Order createOrder(Customer customer);
    public Order updateOrderAddConfiguration(Order order, Configuration configuration);
    public Order updateOrderComplete(Order order);
    public Order readOrderByCustomer(Customer customer);
    public boolean createPayment(String username, String password, Order order);
}
