package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}