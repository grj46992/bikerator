package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.repository.*;
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
    public void createDepotItem(DepotItem depotItem) {
        depotItemRepository.save(depotItem);
    }
}
