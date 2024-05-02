package com.inventorymanagementsystem.entity;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Carte implements Comparable<Carte> {

    private Integer id;
    private Compte numCompte;
    private java.util.Date dateEmission;
    private java.util.Date dateExpiration;
    private Integer cvv;
    private String plafond;
    private String type;
    private String etat;

    // New property for selection
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    // Constructors
    public Carte() {
    }

    public Carte(Integer id, Compte numCompte, java.util.Date dateEmission, java.util.Date dateExpiration, Integer cvv, String plafond, String type, String etat) {
        this.id = id;
        this.numCompte = numCompte;
        this.dateEmission = dateEmission;
        this.dateExpiration = dateExpiration;
        this.cvv = cvv;
        this.plafond = plafond;
        this.type = type;
        this.etat = etat;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compte getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(Compte numCompte) {
        this.numCompte = numCompte;
    }

    public java.util.Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(java.util.Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public java.util.Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(java.util.Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getPlafond() {
        return plafond;
    }

    public void setPlafond(String plafond) {
        this.plafond = plafond;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // New getter and setter for the selected property
    public SimpleBooleanProperty isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return Objects.equals(id, carte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    @Override
    public int compareTo(Carte carte) {
        // Implement your comparison logic here
        return 0;
    }
}
