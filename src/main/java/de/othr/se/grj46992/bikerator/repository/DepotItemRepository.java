package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.DepotItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotItemRepository extends CrudRepository<DepotItem, Long> {
}
