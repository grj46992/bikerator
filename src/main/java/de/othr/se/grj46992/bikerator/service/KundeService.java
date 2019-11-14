package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Kunde;
import de.othr.se.grj46992.bikerator.repository.KundeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KundeService implements KundeServiceIF {

    @Autowired
    private KundeRepository kundeRepository;

    @Override
    public Kunde kundeAnlegen(Kunde kunde) {
        Kunde neu = kundeRepository.save(kunde);
        return neu;
    }

    @Override
    public Kunde kundeAnmelden(String username, int passwort) {
        Kunde benutzer = kundeRepository.findByUsernameAndPasswort(username, passwort);
        return benutzer;
    }
}