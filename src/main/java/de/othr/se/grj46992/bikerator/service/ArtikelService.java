package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Artikel;
import de.othr.se.grj46992.bikerator.repository.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtikelService implements ArtikelServiceIF {

    @Autowired
    private ArtikelRepository artikelRepository;

    @Override
    public Artikel artikelAnlegen(Artikel artikel) {
        Artikel neu = artikelRepository.save(artikel);
        return neu;
    }
}