package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Bestellung {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long bestId;

    private String zeit;

    public Long getBestId() {
        return bestId;
    }

    public void setBestId(Long bestId) {
        this.bestId = bestId;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }

    public Long getWkId() {
        return wkId;
    }

    public void setWkId(Long wkId) {
        this.wkId = wkId;
    }

    private Long wkId;
    //private Versandart versandart;
    //private Zahlungsart zahlungsart;
}