package com.inventorymanagementsystem.entity;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class TypeVirement implements Comparable<TypeVirement> {

    private Integer id;
    private Double frais;
    private String nom;

    // New property for selection
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    // Constructor
    public TypeVirement() {
    }

    public TypeVirement(Integer id, Double frais, String nom) {
        this.id = id;
        this.frais = frais;
        this.nom = nom;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getFrais() {
        return frais;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
        TypeVirement that = (TypeVirement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    @Override
    public int compareTo(TypeVirement typeVirement) {
        // Implement your comparison logic here
        return 0;
    }
}
