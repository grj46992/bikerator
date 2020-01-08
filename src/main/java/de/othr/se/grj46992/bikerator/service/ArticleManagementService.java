package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.repository.*;
import org.aspectj.lang.reflect.InterTypeMethodDeclaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public Iterable<Category> findAllCategories() {
        Iterable<Category> allCategories = categoryRepository.findAll();
        return allCategories;
    }

    @Override
    public Iterable<Item> findAllItems() {
        Iterable<Item> allItems = itemRepository.findAll();
        return allItems;
    }

    @Override
    public Iterable<ItemPool> findAllItemPools() {
        Iterable<ItemPool> allItems = itemPoolRepository.findAll();
        return allItems;
    }

    @Override
    public Iterable<Category> findChildCategories(String category) {
        Category current = categoryRepository.findByName(category);
        if (current.isFather()) {
            Iterable<Category> children = current.getChildCategories();
            return children;
        } else return null;
    }

    @Override
    public Iterable<Item> findItemsByCategory(String category) {
        Category current = categoryRepository.findByName(category);
        if (current.isFather()) {
            Collection<Category> children = current.getChildCategories();
            Iterable<Item> items = itemRepository.findByCategoryIn(children);
            return items;
        }
        else {
            Iterable<Item> items = itemRepository.findByCategory(current);
            return items;
        }
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
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
    public void updateItemPool(ItemPool itemPool) {
        itemPoolRepository.save(itemPool);
    }

    @Override
    public void createDepotItem(DepotItem depotItem) {
        depotItemRepository.save(depotItem);
    }

    @Override
    public Iterable<Item> findItemsByItemPoolListAndCategory(List<ItemPool> itemPoolList, String category) {
        System.out.println("1-----" + itemPoolList.size() + "\n");
        List<Item> completeItemList = new ArrayList<Item>();
        Category currentCategory = categoryRepository.findByName(category);
        System.out.println("2-----" + currentCategory.getName() + "\n");
        for (ItemPool itemPool: itemPoolList) {
            Optional<ItemPool> optional = itemPoolRepository.findById(itemPool.getItemPoolId());
            if (optional.isPresent()) {
                ItemPool currentItemPool = optional.get();
                System.out.println("3-----" + currentItemPool.getItemList().size() + "\n");
                Collection<Item> itemList = currentItemPool.getItemList();
                for (Item item: itemList) {
                    System.out.println("4-----" + item.getCategory().getName() + "\n");
                    if (item.getCategory().equals(currentCategory)) {
                        System.out.println("add something\n");
                        completeItemList.add(item);
                    }
                }
            }
        }
        return completeItemList;
    }

    @Override
    public Configuration updateConfiguration(Configuration currentConfig, Long itemId){
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
                    // Notice Item to delete
                    if (cat.equals(tempCat)) {
                        iterator.remove();
                    }
                }
                // Delete Item and add new Item
                currentConfig.addItem(newItem);
            }

            return currentConfig;
        }
        else {
            return currentConfig;
        }
    }

    @Override
    public List<ItemPool> getItemPoolListFromConfiguration(Configuration configuration, String currentCategory) {
        if (configuration.getItemList().isEmpty()) {
            return null;
        } else {
            List<Item> itemList = configuration.getItemList();
            for (Item item: itemList) {
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
}
