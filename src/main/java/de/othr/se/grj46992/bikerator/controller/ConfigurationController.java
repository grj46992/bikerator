package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ConfigurationController {

    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;

    private static final int MAX_NUMBER_OF_CONFIGURATIONS = 6;

    @RequestMapping("/createConfiguration/start")
    public String createConfigurationStart(
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        //Create Configuration in Session
        session.setAttribute("configuration", new Configuration());

        //Load Categories and Items
        Iterable<Category> children = articleManagementService.findChildCategories(articleManagementService.findFirstCategory());
        HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
        for (Category c: children) {
            itemMap.put(c.getName(), articleManagementService.findItemsByCategory(c.getName()));
        }
        model.addAttribute("itemMap", itemMap);
        model.addAttribute("currentCategory", 0);
        return "createConfiguration/start";
    }

    @RequestMapping("/createConfiguration")
    public String createConfiguration(
            HttpSession session,
            Principal principal,
            Model model
    ) {

        //Create Configuration in Session if not exists
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");

        int currentCategoryIndex = articleManagementService.findLastIndexByConfigurationItemList(currentConfig);
        int nextCategoryIndex = currentCategoryIndex + 1;
        if (articleManagementService.findCategoryByIndex(nextCategoryIndex) == null) {
            // Next Category does not exists
            model.addAttribute("currentCategory", nextCategoryIndex);
            model.addAttribute("config", session.getAttribute("configuration"));
            return "createConfiguration/complete";
        } else {
            //Load Categories and Items
            Iterable<Category> children = articleManagementService.findChildCategories(articleManagementService.findCategoryByIndex(nextCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.findItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.findCategoryByIndex(currentCategoryIndex));

            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("config", session.getAttribute("configuration"));
            model.addAttribute("currentCategory", nextCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping("/createConfiguration/next")
    public String createConfigurationNext(
            @ModelAttribute("itemId") Long itemId,
            @ModelAttribute("currentCategory") int currentCategoryIndex,
            HttpSession session,
            Principal principal,
            Model model
    ) {

        //Create Configuration in Session if not exists
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        if (currentConfig != null) {
            Configuration updatedConfig = articleManagementService.updateConfigurationItemListAddItem(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            session.setAttribute("configuration", new Configuration());
        }

        int nextCategoryIndex = currentCategoryIndex + 1;
        if (articleManagementService.findCategoryByIndex(nextCategoryIndex) == null) {
            // Next Category does not exists
            model.addAttribute("currentCategory", nextCategoryIndex);
            model.addAttribute("config", session.getAttribute("configuration"));
            return "createConfiguration/complete";
        } else {
            //Load Categories and Items
            Iterable<Category> children = articleManagementService.findChildCategories(articleManagementService.findCategoryByIndex(nextCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.findItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.findCategoryByIndex(currentCategoryIndex));

            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("config", session.getAttribute("configuration"));
            model.addAttribute("currentCategory", nextCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping("/createConfiguration/back")
    public String createConfigurationBack(
            @ModelAttribute("currentCategory") int currentCategoryIndex,
            HttpSession session,
            Principal principal,
            Model model
    ) {

        int previousCategoryIndex = currentCategoryIndex - 1;
        if (previousCategoryIndex == 0) {
            //Previous Category is first Category
            //Create Configuration in Session if not exists
            session.setAttribute("configuration", new Configuration());

            //Load Categories and Items
            Iterable<Category> children = articleManagementService.findChildCategories(articleManagementService.findFirstCategory());
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c: children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByCategory(c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("currentCategory", 0);
            return "createConfiguration/start";
        } else {

            Configuration currentConfig = (Configuration) session.getAttribute("configuration");
            if (currentConfig != null) {
                Configuration updatedConfig = articleManagementService.updateConfigurationItemListRemoveItem(currentConfig, articleManagementService.findCategoryByIndex(previousCategoryIndex));
                session.setAttribute("configuration", updatedConfig);
            } else {
                session.setAttribute("configuration", new Configuration());
            }

            //Load Categories and Items
            Iterable<Category> children = articleManagementService.findChildCategories(articleManagementService.findCategoryByIndex(previousCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.findItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.findCategoryByIndex(previousCategoryIndex - 1));

            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
            model.addAttribute("config", session.getAttribute("configuration"));
            model.addAttribute("currentCategory", previousCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping("/createConfiguration/complete")
    public String createConfigurationComplete(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        model.addAttribute("config", session.getAttribute("configuration"));
        return "createConfiguration/complete";
    }

    @RequestMapping("/user/editConfiguration/save")
    public String createConfigurationSave(
            @ModelAttribute("name") String name,
            @ModelAttribute("beschreibung") String description,
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.findByUsername(principal.getName());
        Configuration currentConfig;
        Long configurationId;
        if (configId != null) {
            currentConfig = articleManagementService.findConfigurationById(configId);
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
                currentConfig = (Configuration) session.getAttribute("configuration");
                currentConfig.setCreateDate(new Date(System.currentTimeMillis()));
                currentConfig.setName(name);
                currentConfig.setDescription(description);
                configurationId = articleManagementService.createConfiguration(currentConfig);
                customerManagementService.updateCustomerConfigurationList(user, articleManagementService.findConfigurationById(configurationId));
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("configList", user.getConfigList());
        return "user/myConfigurations";
    }

    @RequestMapping("/user/editConfiguration")
    public String editConfigurations(
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {

        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        if (configId != null) {
            currentConfig = articleManagementService.findConfigurationById(configId);
            model.addAttribute("configExists", true);
        }
        model.addAttribute("config", currentConfig);
        return "user/editConfiguration";
    }

    @RequestMapping("/user/deleteConfiguration")
    public String deleteConfigurations(
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.findByUsername(principal.getName());
        Configuration currentConfig = articleManagementService.findConfigurationById(configId);
        articleManagementService.deleteConfiguration(currentConfig, user);
        model.addAttribute("user", user);
        model.addAttribute("configList", user.getConfigList());
        return "user/myConfigurations";
    }

    @RequestMapping("/user/myConfigurations")
    public String myConfigurations(
            @RequestParam(required = false, name = "id") Long configId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Customer user = customerManagementService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("configList",  user.getConfigList());
        return "user/myConfigurations";
    }
}
