package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Category;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.ItemPool;
import de.othr.se.grj46992.bikerator.entity.Item;

public interface ArticleManagementServiceIF {
    public Category createCategory(Category category);

    public Configuration createConfiguration(Configuration configuration);

    public ItemPool createItemPool(ItemPool itemPool);

    public Item createItem(Item item);

}
