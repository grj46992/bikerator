package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Configuration;
import de.othr.se.grj46992.bikerator.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService implements ConfigurationServiceIF {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public Configuration createConfiguration(Configuration configuration) {
        Configuration newConfiguration = configurationRepository.save(configuration);
        return newConfiguration;
    }
}