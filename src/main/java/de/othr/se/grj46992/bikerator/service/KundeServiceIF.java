package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.Kunde;

public interface KundeServiceIF {

    public Kunde kundeAnlegen(Kunde kunde);
    public Kunde kundeAnmelden(String username, int passwort);
}
