package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;
import java.util.List;

public interface ArticleManagementServiceIF {

    public void createCategory(Category category);
    public void createItem(Item item);
    public void createItemPool(ItemPool itemPool);
    public void createDepotItem(DepotItem depotItem);
    public Iterable<Item> readItemsByCategory(String category);
    public Iterable<Item> readItemsByItemPoolListAndCategory(List<ItemPool> itemPoolList, String category);
    public Iterable<Category> readChildCategories(String category);
    public String readFirstCategory();
    public String readCategoryByIndex(int index);
    public int readLastIndexByConfigurationItemList(Configuration currentConfiguration);
    public Long updateConfiguration(Configuration configuration);
    public Configuration updateConfigurationItemListAddItem(Configuration currentConfig, Long itemId);
    public Configuration updateConfigurationItemListRemoveItem(Configuration currentConfig, String currentCategory);
    public Long createConfiguration(Configuration config);
    public void deleteConfiguration(Configuration configuration, Customer user);
    public Configuration readConfigurationById(Long configurationId);
    public List<ItemPool> readItemPoolListByConfiguration(Configuration configuration, String currentCategory);
    public boolean updateDepotItems(Order order);
}
