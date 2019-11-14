package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Adresse;
import de.othr.se.grj46992.bikerator.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdresseService implements AdresseServiceIF {

    @Autowired
    private AdresseRepository adresseRepository;

    @Override
    public Adresse adresseAnlegen(Adresse adresse) {
        Adresse neu = adresseRepository.save(adresse);
        return neu;
    }
}