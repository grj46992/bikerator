package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.Category;
import de.othr.se.grj46992.bikerator.entity.Item;
import de.othr.se.grj46992.bikerator.entity.ItemPool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findByCategory(Category category);
    Iterable<Item> findByCategoryIn(Collection<Category> categories);
    Item findByName(String name);
}
