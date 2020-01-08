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
    public Iterable<Item> findAllItems();
    public Iterable<ItemPool> findAllItemPools();
    public Configuration updateConfiguration(Configuration currentConfig, Long itemId);

    public List<ItemPool> getItemPoolListFromConfiguration(Configuration configuration, String currentCategory);
}
