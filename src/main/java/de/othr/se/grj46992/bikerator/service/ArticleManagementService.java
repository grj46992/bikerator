package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ArticleManagementService implements ArticleManagementServiceIF {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ItemPoolRepository itemPoolRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DepotItemRepository depotItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Sequence of categories in configuration
    private String[] categoryOrder = {"Fahrradrahmen", "Fahrradfelgen", "Fahrradreifen", "Fahrradschaltwerke"};

    @Override
    public void createItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void createItemPool(ItemPool itemPool) {
        itemPoolRepository.save(itemPool);
    }

    @Override
    public void updateItemPool(ItemPool itemPool) {
        itemPoolRepository.save(itemPool);
    }

    @Override
    public void createDepotItem(DepotItem depotItem) {
        depotItemRepository.save(depotItem);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Iterable<Category> readFatherCategories() {
        return categoryRepository.findByFatherCategory(null);
    }

    @Override
    public String readFirstCategory() {
        return categoryRepository.findByName(this.categoryOrder[0]).getName();
    }

    @Override
    public String readCategoryByIndex(int index) {
        if (index <= this.categoryOrder.length - 1) {
            return categoryRepository.findByName(this.categoryOrder[index]).getName();
        } else {
            return null;
        }
    }

    @Override
    public int readLastIndexByConfigurationItemList(Configuration currentConfiguration) {
        List<Item> itemList = currentConfiguration.getItemList();
        // Check if configuration contains item of last category
        for (int i = categoryOrder.length - 1; i > 0; i--) {
            String category = categoryOrder[i];
            for (Item item : itemList) {
                if (item.getCategory().getFatherCategory().getName().equals(category)) {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    public Iterable<Category> readChildCategories(String category) {
        Category currentCategory = categoryRepository.findByName(category);
        if (currentCategory.isFather()) {
            Iterable<Category> children = currentCategory.getChildCategories();
            return children;
        } else {
            return null;
        }
    }

    @Override
    public Iterable<Item> readItemsByCategory(String category) {
        Category current = categoryRepository.findByName(category);
        if (current.isFather()) {
            Collection<Category> children = current.getChildCategories();
            List<Item> items = itemRepository.findByCategoryIn(children);
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                List<DepotItem> depotItemList = depotItemRepository.findAllByItem((Item) iterator.next());
                if (depotItemList.isEmpty()) {
                    iterator.remove();
                }
            }
            return items;
        } else {
            List<Item> items = itemRepository.findByCategory(current);
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                List<DepotItem> depotItemList = depotItemRepository.findAllByItem((Item) iterator.next());
                if (depotItemList.isEmpty()) {
                    iterator.remove();
                }
            }
            return items;
        }
    }

    @Override
    public Iterable<Item> readItemsByItemPoolListAndCategory(List<ItemPool> itemPoolList, String category) {
        List<Item> completeItemList = new ArrayList<Item>();
        Category currentCategory = categoryRepository.findByName(category);
        // Add all items of given category that are available and part of given item pools
        for (ItemPool itemPool : itemPoolList) {
            Optional<ItemPool> optional = itemPoolRepository.findById(itemPool.getId());
            if (optional.isPresent()) {
                ItemPool currentItemPool = optional.get();
                Collection<Item> itemList = currentItemPool.getItemList();
                for (Item item : itemList) {
                    List<DepotItem> depotItemList = depotItemRepository.findAllByItem(item);
                    if (item.getCategory().equals(currentCategory) && !depotItemList.isEmpty()) {
                        completeItemList.add(item);
                    }
                }
            }
        }
        return completeItemList;
    }

    @Override
    public Configuration updateConfigurationItemListAddItem(Configuration currentConfig, Long itemId) {
        Optional<Item> optional = itemRepository.findById(itemId);
        if (optional.isPresent()) {
            Item newItem = optional.get();
            Category cat = newItem.getCategory();
            // If father category exists, take father category
            if (cat.getFatherCategory() != null) {
                cat = cat.getFatherCategory();
            }
            List<Item> itemList = currentConfig.getItemList();
            if (itemList.isEmpty()) {
                // Add new item if list is empty
                currentConfig.addItem(newItem);
            } else {
                ListIterator<Item> iterator = itemList.listIterator();
                while (iterator.hasNext()) {
                    Category tempCat = iterator.next().getCategory();
                    // If father category exists, take father category
                    if (tempCat.getFatherCategory() != null) {
                        tempCat = tempCat.getFatherCategory();
                    }
                    // Check if item of same category already exists in configuration and remove it
                    if (cat.equals(tempCat)) {
                        iterator.remove();
                    }
                }
                // Add new item to configuration
                currentConfig.addItem(newItem);
            }
            // Update total amount of configuration an round it
            Double newAmountTotal = 0.0;
            for (Item item : itemList) {
                newAmountTotal += item.getPrice();
            }
            currentConfig.setAmountTotal(Math.round(newAmountTotal * 100.0) / 100.0);
            return currentConfig;
        } else {
            return currentConfig;
        }
    }

    @Override
    public Configuration updateConfigurationItemListRemoveItem(Configuration currentConfig, String currentCategory) {
        Category cat = categoryRepository.findByName(currentCategory);
        // If father category exists, take father category
        if (cat.getFatherCategory() != null) {
            cat = cat.getFatherCategory();
        }
        List<Item> itemList = currentConfig.getItemList();
        ListIterator<Item> iterator = itemList.listIterator();
        while (iterator.hasNext()) {
            Category tempCat = iterator.next().getCategory();
            if (tempCat.getFatherCategory() != null) {
                tempCat = tempCat.getFatherCategory();
            }
            // Delete recently added item
            if (cat.equals(tempCat)) {
                iterator.remove();
            }
        }
        // Update total amount of configuraion and round it
        Double newAmountTotal = 0.0;
        for (Item item : itemList) {
            newAmountTotal += item.getPrice();
        }
        currentConfig.setAmountTotal(Math.round(newAmountTotal * 100.0) / 100.0);
        return currentConfig;
    }

    @Override
    public Long updateConfiguration(Configuration configuration) {
        Configuration updatedConfig = configurationRepository.save(configuration);
        return updatedConfig.getId();
    }

    @Override
    public List<ItemPool> readItemPoolListByConfiguration(Configuration configuration, String currentCategory) {
        if (currentCategory.equals("Fahrradreifen")) {
            currentCategory = "Fahrradrahmen";
        }
        if (configuration.getItemList().isEmpty()) {
            return null;
        } else {
            List<Item> itemList = configuration.getItemList();
            for (Item item : itemList) {
                if (item.getCategory().getFatherCategory() != null) {
                    if (item.getCategory().getFatherCategory().getName().equals(currentCategory)) {
                        return item.getItemPoolList();
                    }
                } else {
                    if (item.getCategory().getName().equals(currentCategory)) {
                        return item.getItemPoolList();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Long createConfiguration(Configuration configuration) {
        Configuration newConf = configurationRepository.save(configuration);
        return newConf.getId();
    }

    @Override
    public void deleteConfiguration(Configuration configuration, Customer currentUser) {
        Optional<Customer> optional = customerRepository.findById(currentUser.getUsername());
        if (optional.isPresent()) {
            Customer user = optional.get();
            List<Configuration> userConfigList = user.getConfigList();
            if (userConfigList.contains(configuration)) {
                userConfigList.remove(configuration);
                customerRepository.save(user);
            }
        }
    }

    @Override
    public Configuration readConfigurationById(Long configurationId) {
        Optional<Configuration> optional = configurationRepository.findById(configurationId);
        if (optional.isPresent()) {
            Configuration config = optional.get();
            return config;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateDepotItems(Order order) {
        for (Configuration config : order.getConfigList()) {
            for (Item item : config.getItemList()) {
                List<DepotItem> depotItemList = depotItemRepository.findAllByItem(item);
                if (!depotItemList.isEmpty()) {
                    DepotItem depotItem = depotItemList.get(0);
                    depotItemRepository.deleteById(depotItem.getId());
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
