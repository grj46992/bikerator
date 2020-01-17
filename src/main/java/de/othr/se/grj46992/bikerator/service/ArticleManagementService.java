package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
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

    private String[] categoryOrder = {"Fahrradrahmen", "Fahrradfelgen", "Fahrradreifen", "Fahrradschaltwerke"};

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
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Iterable<Category> readFatherCategories() {
        return categoryRepository.findByFatherCategory(null);
    }

    @Override
    public void createItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void createItemPool(ItemPool itemPool) {
        itemPoolRepository.save(itemPool);
    }

    @Override
    public void createDepotItem(DepotItem depotItem) {
        depotItemRepository.save(depotItem);
    }

    @Override
    public Iterable<Item> readItemsByItemPoolListAndCategory(List<ItemPool> itemPoolList, String category) {
        List<Item> completeItemList = new ArrayList<Item>();
        Category currentCategory = categoryRepository.findByName(category);
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
        // Check if item exists
        if (optional.isPresent()) {
            Item newItem = optional.get();
            // Check if item of same category already exists in configuration
            Category cat = newItem.getCategory();
            // If fatherCategory exists, take fatherCategory
            if (cat.getFatherCategory() != null) {
                cat = cat.getFatherCategory();
            }
            List<Item> itemList = currentConfig.getItemList();
            if (itemList.isEmpty()) {
                // Add new Item if list ist empty
                currentConfig.addItem(newItem);
            } else {
                ListIterator<Item> iterator = itemList.listIterator();
                while (iterator.hasNext()) {
                    Category tempCat = iterator.next().getCategory();
                    // If fatherCategory exists, take fatherCategory
                    if (tempCat.getFatherCategory() != null) {
                        tempCat = tempCat.getFatherCategory();
                    }
                    // Delete Item
                    if (cat.equals(tempCat)) {
                        iterator.remove();
                    }
                }
                // Add new Item
                currentConfig.addItem(newItem);
            }
            // Update AmountTotal
            Double newAmountTotal = 0.0;
            for (Item item : itemList) {
                newAmountTotal += item.getPrice();
            }
            currentConfig.setAmountTotal(newAmountTotal);
            return currentConfig;
        } else {
            return currentConfig;
        }
    }

    @Override
    public Configuration updateConfigurationItemListRemoveItem(Configuration currentConfig, String currentCategory) {
        Category cat = categoryRepository.findByName(currentCategory);
        // If fatherCategory exists, take fatherCategory
        if (cat.getFatherCategory() != null) {
            cat = cat.getFatherCategory();
        }
        List<Item> itemList = currentConfig.getItemList();
        ListIterator<Item> iterator = itemList.listIterator();
        while (iterator.hasNext()) {
            Category tempCat = iterator.next().getCategory();
            // If fatherCategory exists, take fatherCategory
            if (tempCat.getFatherCategory() != null) {
                tempCat = tempCat.getFatherCategory();
            }
            // Delete Item
            if (cat.equals(tempCat)) {
                iterator.remove();
            }
        }
        // Update AmountTotal
        Double newAmountTotal = 0.0;
        for (Item item : itemList) {
            newAmountTotal += item.getPrice();
        }
        currentConfig.setAmountTotal(newAmountTotal);
        return currentConfig;
    }

    @Override
    public Long updateConfiguration(Configuration configuration) {
        Configuration updatedConfig = configurationRepository.save(configuration);
        return updatedConfig.getId();
    }

    @Override
    public List<ItemPool> readItemPoolListByConfiguration(Configuration configuration, String currentCategory) {
        // TODO besser lösen
        if (currentCategory == "Fahrradreifen") {
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

    @Transactional
    @Override
    public void deleteConfiguration(Configuration configuration, Customer currentUser) {
        Optional<Customer> optional = customerRepository.findById(currentUser.getUsername());
        // Check if item exists
        if (optional.isPresent()) {
            Customer user = optional.get();
            List<Configuration> configList = user.getConfigList();
            if (configList.contains(configuration)) {
                if (configList.remove(configuration)) {
                    customerRepository.save(user);
                    configurationRepository.deleteById(configuration.getId());
                }
            } else {
                configurationRepository.deleteById(configuration.getId());
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
    @Transactional
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
