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

import java.util.Date;

@Service
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
        //TODO if shipping address -> set , else set billing adress as shipping adress
        Order currentOrder = orderRepository.save(newOrder);
        customer.setCurrentOrder(currentOrder);
        customerRepository.save(customer);
        return currentOrder;
    }

    @Override
    public Order updateOrderAddConfiguration(Order order, Configuration configuration) {
        order.addConfiguration(configuration);
        Double newAmountTotal = order.getAmountOrder() + configuration.getAmountTotal();
        order.setAmountOrder(Math.round(newAmountTotal * 100.0) / 100.0);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderRemoveConfiguration(Order order, Configuration configuration) {
        order.removeConfiguration(configuration);
        if (order.getConfigList().size() < 1) {
            Customer customer = order.getCustomer();
            customer.setCurrentOrder(null);
            customerRepository.save(customer);
            orderRepository.delete(order);
            return null;
        } else {
            Double newAmountTotal = order.getAmountOrder() - configuration.getAmountTotal();
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
        return orderRepository.findByCustomerAndCompleted(customer, false);
    }

    @Override
    public boolean createTransaction(String email, String password, Order order) throws RestClientException {
        //TODO create payment object and send it to payment service
        Transaction transaction = new Transaction();
        de.othr.daj.megabikeshop.moneyboi.entity.Customer sender = new de.othr.daj.megabikeshop.moneyboi.entity.Customer();
        de.othr.daj.megabikeshop.moneyboi.entity.Customer receiver = new de.othr.daj.megabikeshop.moneyboi.entity.Customer();
        sender.setEmailAddress("rich@dummy"); // String email
        sender.setPassword("asd"); // String password
        receiver.setEmailAddress("poor@dummy"); // EMail des Shops
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(order.getAmountOrder());
        //restServiceClient.postForObject("http://im-codd:8873/restapi/transactions", transaction, CLASS? );
        return true;
    }
}