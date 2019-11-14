package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.Bestellung;
import org.springframework.data.repository.CrudRepository;

public interface BestellungRepository extends CrudRepository<Bestellung, Long> {
}