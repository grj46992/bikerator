package de.othr.se.grj46992.bikerator.repository;

import de.othr.se.grj46992.bikerator.entity.Kunde;
import org.springframework.data.repository.CrudRepository;


public interface KundeRepository extends CrudRepository<Kunde, Long> {
    Kunde findByUsernameAndPasswort(String username, int passwort);
}
