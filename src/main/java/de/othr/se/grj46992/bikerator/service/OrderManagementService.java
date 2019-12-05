package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Order;
import de.othr.se.grj46992.bikerator.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementService implements OrderManagementServiceIF {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        Order newOrder = orderRepository.save(order);
        return newOrder;
    }
}
