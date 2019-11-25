package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Item;
import de.othr.se.grj46992.bikerator.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements ItemServiceIF {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item createItem(Item item) {
        Item newItem = itemRepository.save(item);
        return newItem;
    }
}