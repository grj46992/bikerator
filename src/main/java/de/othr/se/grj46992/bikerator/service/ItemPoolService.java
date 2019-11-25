package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.ItemPool;
import de.othr.se.grj46992.bikerator.repository.ItemPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPoolService implements ItemPoolServiceIF {

    @Autowired
    private ItemPoolRepository itemPoolRepository;

    @Override
    public ItemPool createItemPool(ItemPool itemPool) {
        ItemPool newItemPool = itemPoolRepository.save(itemPool);
        return newItemPool;
    }
}
