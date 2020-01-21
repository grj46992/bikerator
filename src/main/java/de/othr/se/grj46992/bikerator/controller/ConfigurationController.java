package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ConfigurationController {

    // Maximum number of customers configurations
    private static final int MAX_NUMBER_OF_CONFIGURATIONS = 6;
    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;

    @RequestMapping(value = "/createConfiguration/start")
    public String createConfigurationStart(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        if (principal != null && customerManagementService.readById(principal.getName()).getConfigList().size() >= MAX_NUMBER_OF_CONFIGURATIONS) {
            // Set attribute if maximum number of configuration is reached
            model.addAttribute("max", true);
            return "createConfiguration/start";
        } else {
            // Create new configuration instance in session
            session.setAttribute("configuration", new Configuration());

            // Load items for the first step and map it with categories
            Iterable<Category> children = articleManagementService.readChildCategories(articleManagementService.readFirstCategory());
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.readItemsByCategory(c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("currentCategory", 0);
            return "createConfiguration/start";
        }
    }

    @RequestMapping(value = "/createConfiguration")
    public String createConfiguration(
            HttpSession session,
            Model model
    ) {
        // Load current configuration from session
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");

        // Set index of current category and next category
        int currentCategoryIndex = articleManagementService.readLastIndexByConfigurationItemList(currentConfig);
        int nextCategoryIndex = currentCategoryIndex + 1;
        if (articleManagementService.readCategoryByIndex(nextCategoryIndex) == null) {
            // Next Category does not exists -> load template for completing configuration
            model.addAttribute("currentCategory", nextCategoryIndex);
            model.addAttribute("config", session.getAttribute("configuration"));
            return "createConfiguration/complete";
        } else {
            // Next category exists -> get items for current step and map to next category
            Iterable<Category> children = articleManagementService.readChildCategories(articleManagementService.readCategoryByIndex(nextCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.readItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.readCategoryByIndex(currentCategoryIndex));
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.readItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("config", session.getAttribute("configuration"));
            model.addAttribute("currentCategory", nextCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping(value = "/createConfiguration/next", method = RequestMethod.POST)
    public String createConfigurationNext(
            @RequestParam("itemId") Long itemId,
            @ModelAttribute("currentCategory") int currentCategoryIndex,
            HttpSession session,
            Model model
    ) {
        // Load current configuration from session
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        if (currentConfig != null) {
            // Add chosen item to configuration and update session
            Configuration updatedConfig = articleManagementService.updateConfigurationItemListAddItem(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            // Create configuration in session if not already exists
            session.setAttribute("configuration", new Configuration());
        }
        // Set next category index
        int nextCategoryIndex = currentCategoryIndex + 1;
        if (articleManagementService.readCategoryByIndex(nextCategoryIndex) == null) {
            // Next Category does not exists -> load template for completing configuration
            Configuration finishedConfig = (Configuration) session.getAttribute("configuration");
            model.addAttribute("currentCategory", nextCategoryIndex);
            model.addAttribute("config", finishedConfig);
            return "createConfiguration/complete";
        } else {
            // Next category exists -> get items for current step and map to next category
            Iterable<Category> children = articleManagementService.readChildCategories(articleManagementService.readCategoryByIndex(nextCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.readItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.readCategoryByIndex(currentCategoryIndex));
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.readItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("config", session.getAttribute("configuration"));
            model.addAttribute("currentCategory", nextCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping(value = "/createConfiguration/back", method = RequestMethod.POST)
    public String createConfigurationBack(
            @ModelAttribute("currentCategory") int currentCategoryIndex,
            HttpSession session,
            Model model
    ) {
        // Set previous category index
        int previousCategoryIndex = currentCategoryIndex - 1;
        if (previousCategoryIndex == 0) {
            // Previous category is first category -> create new configuration in session
            session.setAttribute("configuration", new Configuration());

            // Load items for start and map to category
            Iterable<Category> children = articleManagementService.readChildCategories(articleManagementService.readFirstCategory());
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.readItemsByCategory(c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("currentCategory", 0);
            return "createConfiguration/start";
        } else {
            // Previous category is not first category -> get configuration from session
            Configuration currentConfig = (Configuration) session.getAttribute("configuration");
            if (currentConfig != null) {
                // Remove recently added item from configuration and update session
                Configuration updatedConfig = articleManagementService.updateConfigurationItemListRemoveItem(currentConfig, articleManagementService.readCategoryByIndex(previousCategoryIndex));
                session.setAttribute("configuration", updatedConfig);
            } else {
                session.setAttribute("configuration", new Configuration());
            }
            // Load items for current step and map to next category
            Iterable<Category> children = articleManagementService.readChildCategories(articleManagementService.readCategoryByIndex(previousCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.readItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.readCategoryByIndex(previousCategoryIndex - 1));
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.readItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("config", session.getAttribute("configuration"));
            model.addAttribute("currentCategory", previousCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping(value = "/createConfiguration/complete")
    public String createConfigurationComplete(
            HttpSession session,
            Model model
    ) {
        Configuration finishedConfig = (Configuration) session.getAttribute("configuration");
        model.addAttribute("config", finishedConfig);
        return "createConfiguration/complete";
    }

    @RequestMapping(value = "/user/editConfiguration/save", method = RequestMethod.POST)
    public String createConfigurationSave(
            @ModelAttribute("name") String name,
            @ModelAttribute("beschreibung") String description,
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.readById(principal.getName());
        Configuration currentConfig;

        if (configId != null) {
            // Configuration is already saved -> update name and description
            currentConfig = articleManagementService.readConfigurationById(configId);
            currentConfig.setName(name);
            currentConfig.setDescription(description);
            articleManagementService.updateConfiguration(currentConfig);
        } else {
            if (user.getConfigList().size() >= MAX_NUMBER_OF_CONFIGURATIONS) {
                model.addAttribute("maxNumberError", true);
                model.addAttribute("user", user);
                model.addAttribute("configList", user.getConfigList());
                return "user/myConfigurations";
            } else {
                // Maximum number not reached -> save new configuration and update configurations of customer
                currentConfig = (Configuration) session.getAttribute("configuration");
                currentConfig.setCreateDate(new Date(System.currentTimeMillis()));
                currentConfig.setName(name);
                currentConfig.setDescription(description);
                Long configurationId = articleManagementService.createConfiguration(currentConfig);
                customerManagementService.updateCustomerConfigurationList(user, articleManagementService.readConfigurationById(configurationId));
            }
        }
        session.setAttribute("configuration", null);
        model.addAttribute("user", user);
        model.addAttribute("configList", user.getConfigList());
        return "user/myConfigurations";
    }

    @RequestMapping(value = "/user/editConfiguration", method = RequestMethod.GET)
    public String editConfigurations(
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        if (configId != null) {
            currentConfig = articleManagementService.readConfigurationById(configId);
            model.addAttribute("configExists", true);
        }
        if (currentConfig != null) {
            model.addAttribute("config", currentConfig);
            return "user/editConfiguration";
        } else {
            Customer user = customerManagementService.readById(principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("configList", user.getConfigList());
            return "user/myConfigurations";
        }
    }

    @RequestMapping(value = "/user/deleteConfiguration", method = RequestMethod.GET)
    public String deleteConfigurations(
            @RequestParam(required = false, name = "id") Long configId,
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.readById(principal.getName());
        Configuration currentConfig = articleManagementService.readConfigurationById(configId);
        articleManagementService.deleteConfiguration(currentConfig, user);
        model.addAttribute("user", user);
        model.addAttribute("configList", user.getConfigList());
        return "user/myConfigurations";
    }

    @RequestMapping(value = "/user/myConfigurations")
    public String myConfigurations(
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.readById(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("configList", user.getConfigList());
        return "user/myConfigurations";
    }


    @RequestMapping(value = "/user/postConfiguration", method = RequestMethod.GET)
    public String postConfiguration(
            @RequestParam(required = false, name = "id") Long configId,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = articleManagementService.readConfigurationById(configId);
        Customer customer = customerManagementService.readById(principal.getName());
        try {
            // Create and send post to friendzone
            customerManagementService.createPost(currentConfig.getName(), customer.getEmail());
            model.addAttribute("postIsSet", true);
            return "user/account";
        } catch (RestClientException e) {
            model.addAttribute("postError", true);
            return "/user/postConfiguration";
        }
    }
}
