package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository <ShoppingCart, Long> {
}