package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;

public interface OrderManagementServiceIF {

    Order createOrder(Customer customer);

    Order updateOrderAddConfiguration(Order order, Configuration configuration);

    Order updateOrderComplete(Order order);

    Order readOrderByCustomer(Customer customer);

    boolean createTransaction(String username, String password, Order order);
}
