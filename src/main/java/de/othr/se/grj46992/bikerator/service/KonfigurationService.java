package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Konfiguration;
import de.othr.se.grj46992.bikerator.repository.KonfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KonfigurationService implements KonfigurationServiceIF {

    @Autowired
    private KonfigurationRepository konfigurationRepository;

    @Override
    public Konfiguration konfigurationAnlegen(Konfiguration konfiguration) {
        Konfiguration neu = konfigurationRepository.save(konfiguration);
        return neu;
    }
}