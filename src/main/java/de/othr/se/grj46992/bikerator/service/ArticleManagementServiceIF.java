package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;

import java.util.List;

public interface ArticleManagementServiceIF {

    public void createCategory(Category category);
    public void createItem(Item item);
    public void createItemPool(ItemPool itemPool);
    public void updateItemPool(ItemPool itemPool);
    public void createDepotItem(DepotItem depotItem);
    public Iterable<Category> findAllCategories();
    public Iterable<Item> findItemsByCategory(String category);
    public Iterable<Item> findItemsByItemPoolListAndCategory(List<ItemPool> itemPoolList, String category);
    public Iterable<Category> findChildCategories(String category);
    public String findFirstCategory();
    public String findCategoryByIndex(int index);
    public Iterable<Item> findAllItems();
    public Iterable<ItemPool> findAllItemPools();
    public Long updateConfiguration(Configuration configuration);
    public Configuration updateConfigurationItemList(Configuration currentConfig, Long itemId);
    public Long createConfiguration(Configuration config);
    public Configuration findConfigurationById(Long configurationId);
    public List<ItemPool> findItemPoolListByConfiguration(Configuration configuration, String currentCategory);
}
