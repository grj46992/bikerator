package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.DepotItem;
import de.othr.se.grj46992.bikerator.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepotItemRepository extends CrudRepository<DepotItem, Long> {
    public List<DepotItem> findAllByItem(Item item);
}
