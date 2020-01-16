package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import de.othr.se.grj46992.bikerator.repository.CustomerRepository;
import de.othr.se.grj46992.bikerator.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderManagementService implements OrderManagementServiceIF {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Order createOrder(Customer customer) {
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setBillingAddress(customer.getAddress());
        Order currentOrder = orderRepository.save(newOrder);
        customer.setCurrentOrder(currentOrder);
        customerRepository.save(customer);
        return currentOrder;
    }

    @Override
    public Order updateOrderAddConfiguration(Order order, Configuration configuration) {
        order.addConfiguration(configuration);
        order.setAmountOrder(order.getAmountOrder() + configuration.getAmountTotal());
        return orderRepository.save(order);
    }

    //TODO implement removeConfig from order function

    @Override
    public Order updateOrderComplete(Order order) {
        order.setCompleted(true);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        return orderRepository.save(order);
    }

    @Override
    public Order readOrderByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    @Override
    public boolean createPayment(String username, String password, Order order) {
        //TODO create payment object and send it to payment service

        return true;
    }
}