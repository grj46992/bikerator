package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Warenkorb;
import de.othr.se.grj46992.bikerator.repository.WarenkorbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarenkorbService implements WarenkorbServiceIF {

    @Autowired
    private WarenkorbRepository warenkorbRepository;

    @Override
    public Warenkorb warenkorbAnlegen(Warenkorb warenkorb) {
        Warenkorb neu = warenkorbRepository.save(warenkorb);
        return neu;
    }
}