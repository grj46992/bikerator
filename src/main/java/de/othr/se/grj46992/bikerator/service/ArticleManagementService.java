package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Category;
import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Item;
import de.othr.se.grj46992.bikerator.entity.ItemPool;
import de.othr.se.grj46992.bikerator.repository.CategoryRepository;
import de.othr.se.grj46992.bikerator.repository.ConfigurationRepository;
import de.othr.se.grj46992.bikerator.repository.ItemPoolRepository;
import de.othr.se.grj46992.bikerator.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Item createItem(Item item) {
        Item newItem = itemRepository.save(item);
        return newItem;
    }

    @Override
    public Category createCategory(Category category) {
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    @Override
    public Configuration createConfiguration(Configuration configuration) {
        Configuration newConfiguration = configurationRepository.save(configuration);
        return newConfiguration;
    }

    @Override
    public ItemPool createItemPool(ItemPool itemPool) {
        ItemPool newItemPool = itemPoolRepository.save(itemPool);
        return newItemPool;
    }
}
