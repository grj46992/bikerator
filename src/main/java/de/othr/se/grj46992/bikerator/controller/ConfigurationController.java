package de.othr.se.grj46992.bikerator.controller;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.service.ArticleManagementServiceIF;
import de.othr.se.grj46992.bikerator.service.CustomerManagementServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/createConfiguration/step_1")
    public String createConfigurationStep1(
            HttpSession session,
            Principal principal,
            Model model
    ) {
        //Create Configuration in Session if not exists
        session.setAttribute("configuration", new Configuration());

        //Load Categories and Items
        Iterable<Category> children = articleManagementService.findChildCategories("Fahrradrahmen");
        if (children != null) {
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c: children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByCategory(c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
        } else {
            model.addAttribute("category", articleManagementService.findItemsByCategory("Fahrradrahmen"));
        }

        model.addAttribute("config", (Configuration)session.getAttribute("configuration"));

        return "createConfiguration/step_1";
    }

    @RequestMapping("/createConfiguration/step_2")
    public String createConfigurationStep2(
            @ModelAttribute("itemId") Long itemId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        //Create Configuration in Session if not exists
        Configuration currentConfig = (Configuration)session.getAttribute("configuration");
        if (currentConfig != null) {
            Configuration updatedConfig = articleManagementService.updateConfiguration(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            session.setAttribute("configuration", new Configuration());
        }

        //Load Categories and Items
        Iterable<Category> children = articleManagementService.findChildCategories("Fahrradfelgen");
        List<ItemPool> currentItemPoolList = articleManagementService.getItemPoolListFromConfiguration((Configuration)session.getAttribute("configuration"), "Fahrradrahmen");

        if (children != null) {
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c: children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
        } else {
            model.addAttribute("category", articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, "Fahrradfelgen"));
        }

        model.addAttribute("config", (Configuration)session.getAttribute("configuration"));

        return "createConfiguration/step_2";
    }

    @RequestMapping("/createConfiguration/step_3")
    public String createConfigurationStep3(
            @ModelAttribute("itemId") Long itemId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        //Create Configuration in Session if not exists
        Configuration currentConfig = (Configuration)session.getAttribute("configuration");
        if (currentConfig != null) {
            Configuration updatedConfig = articleManagementService.updateConfiguration(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            session.setAttribute("configuration", new Configuration());
        }

        //Load Categories and Items
        Iterable<Category> children = articleManagementService.findChildCategories("Fahrradreifen");
        List<ItemPool> currentItemPoolList = articleManagementService.getItemPoolListFromConfiguration((Configuration)session.getAttribute("configuration"), "Fahrradfelgen");

        if (children != null) {
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c: children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
        } else {
            model.addAttribute("category", articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, "Fahrradreifen"));
        }

        model.addAttribute("config", (Configuration)session.getAttribute("configuration"));

        return "createConfiguration/step_3";
    }

    @RequestMapping("/createConfiguration/step_4")
    public String createConfigurationStep4(
            @ModelAttribute("itemId") Long itemId,
            HttpSession session,
            Principal principal,
            Model model
    ) {
        //Create Configuration in Session if not exists
        Configuration currentConfig = (Configuration)session.getAttribute("configuration");
        if (currentConfig != null) {
            Configuration updatedConfig = articleManagementService.updateConfiguration(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            session.setAttribute("configuration", new Configuration());
        }

        //Load Categories and Items
        Iterable<Category> children = articleManagementService.findChildCategories("Fahrradschaltwerke");
        List<ItemPool> currentItemPoolList = articleManagementService.getItemPoolListFromConfiguration((Configuration)session.getAttribute("configuration"), "Fahrradrahmen");

        if (children != null) {
            HashMap<String, Iterable<Item>> itemMap = new HashMap<String, Iterable<Item>>();
            for (Category c: children) {
                itemMap.put(c.getName(), articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, c.getName()));
            }
            model.addAttribute("itemMap", itemMap);
        } else {
            model.addAttribute("category", articleManagementService.findItemsByItemPoolListAndCategory(currentItemPoolList, "Fahrradschaltwerke"));
        }

        model.addAttribute("config", (Configuration)session.getAttribute("configuration"));

        return "createConfiguration/step_4";
    }

    @RequestMapping("/createConfiguration/saveConfiguration")
    public String saveConfiguration(
          @ModelAttribute("itemId") Long itemId,
          HttpSession session,
          Principal principal,
          Model model
    ) {
        Configuration currentConfig = (Configuration)session.getAttribute("configuration");
        if (currentConfig != null) {
            Configuration updatedConfig = articleManagementService.updateConfiguration(currentConfig, itemId);
            session.setAttribute("configuration", updatedConfig);
        } else {
            session.setAttribute("configuration", new Configuration());
        }
        model.addAttribute("config", (Configuration)session.getAttribute("configuration"));
        return "createConfiguration/saveConfiguration";
    }
}
