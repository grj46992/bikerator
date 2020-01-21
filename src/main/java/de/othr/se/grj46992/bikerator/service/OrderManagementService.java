package de.othr.se.grj46992.bikerator.service;

import de.othr.daj.megabikeshop.moneyboi.entity.Transaction;
import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import de.othr.se.grj46992.bikerator.repository.CustomerRepository;
import de.othr.se.grj46992.bikerator.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class OrderManagementService implements OrderManagementServiceIF {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestTemplate restServiceClient;

    @Override
    public Order createOrder(Customer customer) {
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setBillingAddress(customer.getAddress());
        newOrder.setShippingAddress(customer.getAddress());
        Order currentOrder = orderRepository.save(newOrder);
        customer.setCurrentOrder(currentOrder);
        customerRepository.save(customer);
        return currentOrder;
    }

    @Override
    public Order updateOrderAddConfiguration(Order order, Configuration configuration) {
        order.addConfiguration(configuration);
        double newAmountTotal = order.getAmountOrder() + configuration.getAmountTotal();
        order.setAmountOrder(Math.round(newAmountTotal * 100.0) / 100.0);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderRemoveConfiguration(Order order, Configuration configuration) {
        order.removeConfiguration(configuration);
        if (order.getConfigList().size() < 1) {
            // Order contains no configurations -> delete order
            Customer customer = order.getCustomer();
            customer.setCurrentOrder(null);
            customerRepository.save(customer);
            orderRepository.delete(order);
            return null;
        } else {
            double newAmountTotal = order.getAmountOrder() - configuration.getAmountTotal();
            order.setAmountOrder(Math.round(newAmountTotal * 100.0) / 100.0);
            return orderRepository.save(order);
        }
    }

    @Override
    public void updateOrderAddShippingAddress(Order order, Address shippingAdress) {
        order.setShippingAddress(shippingAdress);
        orderRepository.save(order);
    }

    @Override
    public Order updateOrderComplete(Order order) {
        order.setCompleted(true);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        return orderRepository.save(order);
    }

    @Override
    public Order readOrderByCustomer(Customer customer) {
        return orderRepository.findByCustomerAndCompletedFalse(customer);
    }

    @Override
    public void createTransaction(String email, String password, Order order) throws RestClientException {
        // Create moneyboi-transaction, set sender, receiver and amount
        Transaction transaction = new Transaction();
        de.othr.daj.megabikeshop.moneyboi.entity.Customer sender = new de.othr.daj.megabikeshop.moneyboi.entity.Customer();
        de.othr.daj.megabikeshop.moneyboi.entity.Customer receiver = new de.othr.daj.megabikeshop.moneyboi.entity.Customer();
        sender.setEmailAddress(email);
        sender.setPassword(password);
        receiver.setEmailAddress("bikerator@shop.de");
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(order.getAmountOrder());
        // Send transaction to moneyboi
        restServiceClient.postForObject("http://im-codd:8873/restapi/transactions", transaction, Transaction.class);
    }
}