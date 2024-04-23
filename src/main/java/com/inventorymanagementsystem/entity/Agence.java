package com.inventorymanagementsystem.entity;

import java.util.Collection;
import java.util.Objects;

public class Agence {

    private Integer id;
    private String adresse;
    private String nom;
    private Long num_tel;
    private String etat;
    private Integer id_chef; // Changed to Integer
    private Collection<Integer> agents; // Changed to Collection of Integer
    private Collection<Integer> comptes; // Changed to Collection of Integer
    private java.util.Date date_ajout;
    private String latitude;
    private String longitude;

    // Constructor
    public Agence( Integer id,String adresse, String nom, Long num_tel, String etat, Integer id_chef, java.util.Date date_ajout, String latitude, String longitude) {
        this.id=id;
        this.adresse = adresse;
        this.nom = nom;
        this.num_tel = num_tel;
        this.etat = etat;
        this.id_chef = id_chef;


        this.date_ajout = date_ajout;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }



    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getNumTel() {
        return num_tel;
    }

    public void setNumTel(Long num_tel) {
        this.num_tel = num_tel;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Integer getIdChef() {
        return id_chef;
    }

    public void setIdChef(Integer id_chef) {
        this.id_chef = id_chef;
    }

    public Collection<Integer> getAgents() {
        return agents;
    }

    public void setAgents(Collection<Integer> agents) {
        this.agents = agents;
    }

    public Collection<Integer> getComptes() {
        return comptes;
    }

    public void setComptes(Collection<Integer> comptes) {
        this.comptes = comptes;
    }

    public java.util.Date getDateAjout() {
        return date_ajout;
    }

    public void setDateAjout(java.util.Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agence agence = (Agence) o;
        return Objects.equals(id, agence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agence{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", nom='" + nom + '\'' +
                ", num_tel=" + num_tel +
                ", etat='" + etat + '\'' +
                ", id_chef=" + id_chef +
                ", agents=" + agents +
                ", comptes=" + comptes +
                ", date_ajout=" + date_ajout +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
