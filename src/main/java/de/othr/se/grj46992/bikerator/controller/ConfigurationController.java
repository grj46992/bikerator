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
import java.util.HashMap;
import java.util.List;

@Controller
public class ConfigurationController {

    @Autowired
    private CustomerManagementServiceIF customerManagementService;
    @Autowired
    private ArticleManagementServiceIF articleManagementService;

    @RequestMapping("/createConfiguration/start")
    public String createConfigurationStart(
            HttpSession session,
            Principal principal,
            Model model
    ) {
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
            Configuration updatedConfig = articleManagementService.updateConfiguration(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            session.setAttribute("configuration", new Configuration());
        }

        int nextCategoryIndex = currentCategoryIndex + 1;

        if (articleManagementService.findCategoryByIndex(nextCategoryIndex) == null) {
            // Next Category does not exists
            model.addAttribute("config", (Configuration)session.getAttribute("configuration"));
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

            model.addAttribute("config", (Configuration) session.getAttribute("configuration"));
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
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");

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
            //Load Categories and Items
            Iterable<Category> children = articleManagementService.findChildCategories(articleManagementService.findCategoryByIndex(previousCategoryIndex));
            List<ItemPool> currentItemPoolList = articleManagementService.findItemPoolListByConfiguration((Configuration) session.getAttribute("configuration"), articleManagementService.findCategoryByIndex(previousCategoryIndex - 1));

            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c : children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);

            model.addAttribute("config", (Configuration) session.getAttribute("configuration"));
            model.addAttribute("currentCategory", previousCategoryIndex);
            return "createConfiguration/configuration";
        }
    }

    @RequestMapping("/createConfiguration/save")
    public String createConfigurationSave(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = (Configuration) session.getAttribute("configuration");
        Customer user = customerManagementService.findByUsername(principal.getName());
        articleManagementService.saveConfiguration(currentConfig);
        customerManagementService.updateCustomerConfigurationList(user, currentConfig);
        model.addAttribute("configList", user.getConfigList());
        return "user/editConfiguration";
    }

    @RequestMapping("/user/editConfiguration")
    public String editConfigurations(
            @RequestParam("id") Long configurationId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        Configuration currentConfig = articleManagementService.findConfigurationById(configurationId);
        model.addAttribute("config", currentConfig);
        return "user/editConfiguration";
    }
}
