package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.entity.Customer;
import org.springframework.data.repository.CrudRepository;


public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
}