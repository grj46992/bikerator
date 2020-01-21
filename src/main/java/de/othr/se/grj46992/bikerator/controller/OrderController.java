package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.Address;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import de.othr.se.grj46992.bikerator.entity.Order;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.OrderManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import java.security.Principal;


@Controller
public class OrderController {
    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;
    @Autowired
    private OrderManagementServiceIF orderManagementService;

    @RequestMapping(value = "/user/addToOrder", method = RequestMethod.GET)
    public String addConfigurationToOrder(
            @RequestParam(required = false, name = "id") Long configId,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = articleManagementService.readConfigurationById(configId);
        Customer customer = customerManagementService.readById(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);

        if (currentOrder != null) {
            // Add configuration to current order if order already exists
            currentOrder = orderManagementService.updateOrderAddConfiguration(currentOrder, currentConfig);
        } else {
            // Create new order and add configuration
            currentOrder = orderManagementService.createOrder(customer);
            currentOrder = orderManagementService.updateOrderAddConfiguration(currentOrder, currentConfig);
        }
        model.addAttribute("order", currentOrder);
        return "user/shoppingcart";
    }

    @RequestMapping(value = "/checkout/removeFromOrder", method = RequestMethod.GET)
    public String removeConfigurationFromOrder(
            @RequestParam(required = false, name = "id") Long configId,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = articleManagementService.readConfigurationById(configId);
        Customer customer = customerManagementService.readById(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);
        // Remove chosen configuration from order
        currentOrder = orderManagementService.updateOrderRemoveConfiguration(currentOrder, currentConfig);
        model.addAttribute("order", currentOrder);
        return "user/shoppingcart";
    }

    @RequestMapping(value = "/checkout/completeOrder")
    public String completeOrder(
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readById(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);
        model.addAttribute("user", customer);
        model.addAttribute("order", currentOrder);
        return "checkout/completeOrder";
    }

    @RequestMapping(value = "/checkout/payment", method = RequestMethod.POST)
    public String payment(
            @RequestParam(required = false, defaultValue = "false", name = "shippingAddress") Boolean differentShippingAddress,
            @RequestParam(required = false, name = "street") String street,
            @RequestParam(required = false, name = "number") String number,
            @RequestParam(required = false, name = "postcode") String postcode,
            @RequestParam(required = false, name= "town") String town,
            @RequestParam(required = false, name = "country") String country,
            Principal principal
    ) {
        Customer customer = customerManagementService.readById(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);

        if (differentShippingAddress) {
            // Create shipping address in order if it is not equal to billing address
            Address shippingAddress = new Address();
            shippingAddress.setStreet(street);
            shippingAddress.setNumber(number);
            shippingAddress.setPostcode(postcode);
            shippingAddress.setTown(town);
            shippingAddress.setCountry(country);
            shippingAddress = customerManagementService.createAddress(shippingAddress);
            orderManagementService.updateOrderAddShippingAddress(currentOrder, shippingAddress);
        } else {
            // Set customer address as shipping address
            orderManagementService.updateOrderAddShippingAddress(currentOrder, customer.getAddress());
        }
        return "checkout/payment";
    }

    @RequestMapping(value = "/checkout/complete", method = RequestMethod.POST)
    public String completeCheckout(
            @RequestParam(required = true, value= "payment") String payment,
            @RequestParam(required = false, value= "email") String email,
            @RequestParam(required = false, value = "password") String password,
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readById(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);
        // Check if all items of order are in stock
        boolean allItemsAvailable = articleManagementService.updateDepotItems(currentOrder);
        if (allItemsAvailable) {
            if (payment.equals("moneyboi")) {
                try {
                    // Payment service moneyboi chosen -> create transaction and send it to moneyboi
                    orderManagementService.createTransaction(email, password, currentOrder);
                    Order updatedOrder = orderManagementService.updateOrderComplete(currentOrder);
                    customerManagementService.updateCompletedOrderList(customer, updatedOrder);
                    customerManagementService.deleteCurrentOrder(customer);
                    model.addAttribute("order", updatedOrder);
                    return "checkout/summary";
                } catch (RestClientException e) {
                    model.addAttribute("paymentError", true);
                    return "checkout/payment";
                }
            } else if(payment.equals("bill")) {
                // Payment on account -> complete order
                Order updatedOrder = orderManagementService.updateOrderComplete(currentOrder);
                customerManagementService.updateCompletedOrderList(customer, updatedOrder);
                customerManagementService.deleteCurrentOrder(customer);
                model.addAttribute("order", updatedOrder);
                return "checkout/summary";
            } else {
                model.addAttribute("paymentError", true);
                return "checkout/payment";
            }
        } else {
            model.addAttribute("order", currentOrder);
            model.addAttribute("stockError", true);
            return "user/shoppingcart";
        }
    }
}
