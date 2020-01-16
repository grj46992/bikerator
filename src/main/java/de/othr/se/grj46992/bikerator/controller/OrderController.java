package de.othr.se.grj46992.bikerator.controller;



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
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;


@Controller
public class OrderController {
    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;
    @Autowired
    private OrderManagementServiceIF orderManagementService;

    @RequestMapping("/user/addToOrder")
    public String addConfigurationToOrder(
            @RequestParam(required = false, name = "id") Long configId,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = articleManagementService.readConfigurationById(configId);
        Customer customer = customerManagementService.readByUsername(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);

        if (currentOrder != null) {
            currentOrder = orderManagementService.updateOrderAddConfiguration(currentOrder, currentConfig);
        } else {
            currentOrder = orderManagementService.createOrder(customer);
            currentOrder = orderManagementService.updateOrderAddConfiguration(currentOrder, currentConfig);
        }
        model.addAttribute("order",  currentOrder);
        return "user/shoppingcart";
    }

    @RequestMapping("/checkout/completeOrder")
    public String completeOrder(
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readByUsername(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);
        model.addAttribute("order",  currentOrder);
        return "checkout/completeOrder";
    }

    @RequestMapping("/checkout/payment")
    public String payment() {
        return "checkout/payment";
    }

    @RequestMapping("/checkout/complete")
    public String completeCheckout(
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Principal principal,
            Model model
    ) {
        Customer customer = customerManagementService.readByUsername(principal.getName());
        Order currentOrder = orderManagementService.readOrderByCustomer(customer);

        boolean allItemsAvailable = articleManagementService.updateDepotItems(currentOrder);

        if (allItemsAvailable) {
            boolean orderIsPayed = orderManagementService.createPayment(username, password, currentOrder);
            if (orderIsPayed) {
                Order updatedOrder = orderManagementService.updateOrderComplete(currentOrder);
                customerManagementService.updateCompletedOrderList(customer, updatedOrder);
                customerManagementService.deleteCurrentOrder(customer);
                model.addAttribute("order",  updatedOrder);
                return "checkout/summary";
            } else {
                model.addAttribute("paymentError",  true);
                return "checkout/payment";
            }
        } else {
            model.addAttribute("order",  currentOrder);
            model.addAttribute("stockError",  true);
            return "user/shoppingcart";
        }
    }
}
