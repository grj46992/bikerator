package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Bestellung;
import de.othr.se.grj46992.bikerator.repository.BestellungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BestellungService implements BestellungServiceIF {

    @Autowired
    private BestellungRepository bestellungRepository;

    @Override
    public Bestellung bestellungAnlegen(Bestellung bestellung) {
        Bestellung neu = bestellungRepository.save(bestellung);
        return neu;
    }
}