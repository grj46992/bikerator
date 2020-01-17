package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;

import java.util.List;

public interface ArticleManagementServiceIF {

    void createCategory(Category category);

    void createItem(Item item);

    void createItemPool(ItemPool itemPool);

    void createDepotItem(DepotItem depotItem);

    Iterable<Item> readItemsByCategory(String category);

    Iterable<Item> readItemsByItemPoolListAndCategory(List<ItemPool> itemPoolList, String category);

    Iterable<Category> readChildCategories(String category);

    String readFirstCategory();

    Iterable<Category> readFatherCategories();

    String readCategoryByIndex(int index);

    int readLastIndexByConfigurationItemList(Configuration currentConfiguration);

    Long updateConfiguration(Configuration configuration);

    Configuration updateConfigurationItemListAddItem(Configuration currentConfig, Long itemId);

    Configuration updateConfigurationItemListRemoveItem(Configuration currentConfig, String currentCategory);

    Long createConfiguration(Configuration config);

    void deleteConfiguration(Configuration configuration, Customer user);

    Configuration readConfigurationById(Long configurationId);

    List<ItemPool> readItemPoolListByConfiguration(Configuration configuration, String currentCategory);

    boolean updateDepotItems(Order order);
}
