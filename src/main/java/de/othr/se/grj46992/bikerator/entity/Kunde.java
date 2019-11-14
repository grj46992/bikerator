package de.othr.se.grj46992.bikerator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Kunde {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long kundId;

    private String vorname;
    private String nachname;
    private String username;
    private int passwort;
    //private Long adrId;
    //private Long konfId;

    public Kunde () {
    }

    public Long getKundId() {
        return kundId;
    }

    public void setKundId(Long kundId) {
        this.kundId = kundId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPasswort() {
        return passwort;
    }

    public void setPasswort(int passwort) {
        this.passwort = passwort;
    }

}
