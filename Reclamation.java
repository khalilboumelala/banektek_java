package com.inventorymanagementsystem.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Reclamation implements Comparable<Reclamation> {
    private Integer id;
    private Integer id_client_id;
    //private Date date_reclamation;
    private java.util.Date date_reclamation;
    private String type;
    private String description;
    private String etat;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String document;
    private String email;


    public Reclamation(Integer id, String type, String description, Integer id_client_id, Date date_reclamation, String etat, String document, String email) {
        this.id = id;
        this.id_client_id = id_client_id;
        this.date_reclamation = date_reclamation;
        this.type = type;
        this.description = description;
        this.etat = etat;
        this.document = document;
        this.email = email;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_client_id() {
        return id_client_id;
    }

    public void setId_client_id(Integer id_client_id) {
        this.id_client_id = id_client_id;
    }

    public Date getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(Date date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation reclamation = (Reclamation) o;
        return Objects.equals(id, reclamation.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    //@Override
    public int compareTo(Reclamation reclamation) {
        // Implement your comparison logic here
        return 0;
    }

    public Reclamation() {
        // Initialisez éventuellement des attributs ici si nécessaire
    }
}
