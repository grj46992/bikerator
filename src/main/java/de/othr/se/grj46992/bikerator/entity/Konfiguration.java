package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Konfiguration {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long konfId;

    private String name;
    private String beschreibung;
    private String datum;
    private Double betrag;

    private Long kundId;
    private Long[] artId;

    public Long getKonfId() {
        return konfId;
    }

    public void setKonfId(Long konfId) {
        this.konfId = konfId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Double getBetrag() {
        return betrag;
    }

    public void setBetrag(Double betrag) {
        this.betrag = betrag;
    }

    public Long getKundId() {
        return kundId;
    }

    public void setKundId(Long kundId) {
        this.kundId = kundId;
    }

    public Long[] getArtId() {
        return artId;
    }

    public void setArtId(Long[] artId) {
        this.artId = artId;
    }
}