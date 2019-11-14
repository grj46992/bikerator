package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Artikel {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long artId;

    private Integer bestand;
    private Double preis;
    private Double gewicht;
    private String hersteller;

    public Long getArtId() {
        return artId;
    }

    public void setArtId(Long artId) {
        this.artId = artId;
    }

    public Integer getBestand() {
        return bestand;
    }

    public void setBestand(Integer bestand) {
        this.bestand = bestand;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public Double getGewicht() {
        return gewicht;
    }

    public void setGewicht(Double gewicht) {
        this.gewicht = gewicht;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public Long getKatId() {
        return katId;
    }

    public void setKatId(Long katId) {
        this.katId = katId;
    }

    private Long katId;
}