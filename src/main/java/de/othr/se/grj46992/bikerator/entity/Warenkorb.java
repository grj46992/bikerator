package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Warenkorb {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long wkId;

    private Long kundId;
    private Long konfId;
    private Double gesamtbetrag;

    public Long getWkId() {
        return wkId;
    }

    public void setWkId(Long wkId) {
        this.wkId = wkId;
    }

    public Long getKundId() {
        return kundId;
    }

    public void setKundId(Long kundId) {
        this.kundId = kundId;
    }

    public Long getKonfId() {
        return konfId;
    }

    public void setKonfId(Long konfId) {
        this.konfId = konfId;
    }

    public Double getGesamtbetrag() {
        return gesamtbetrag;
    }

    public void setGesamtbetrag(Double gesamtbetrag) {
        this.gesamtbetrag = gesamtbetrag;
    }
}