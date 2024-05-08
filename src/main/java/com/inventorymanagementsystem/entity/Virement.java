package com.inventorymanagementsystem.entity;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Virement implements Comparable<Virement> {

    private Integer id;
     private Compte idCompteEmetteur;
    private Compte idCompteBeneficiaire;
    private String cinEmetteur;
    private String photoCin;
    private Double montant;
    private java.util.Date dateEmission;
    private java.util.Date dateApprobation;
    private String etat;
    private TypeVirement type;

    // Constructor
    public Virement() {
        this.dateEmission = new java.util.Date();
        this.etat = "Succès";
    }

     public Virement(Integer id, Compte idCompteEmetteur, Compte idCompteBeneficiaire, String cinEmetteur, String photoCin, Double montant, java.util.Date dateEmission, java.util.Date dateApprobation, String etat, TypeVirement type) {
         this.id = id;
         this.idCompteEmetteur = idCompteEmetteur;
         this.idCompteBeneficiaire = idCompteBeneficiaire;
         this.cinEmetteur = cinEmetteur;
         this.photoCin = photoCin;
         this.montant = montant;
         this.dateEmission = dateEmission;
         this.dateApprobation = dateApprobation;
         this.etat = etat;
         this.type = type;
     }
    public Virement(Integer id,  String cinEmetteur, String photoCin, Double montant, java.util.Date dateEmission, java.util.Date dateApprobation, String etat, TypeVirement type) {
        this.id = id;

        this.cinEmetteur = cinEmetteur;
        this.photoCin = photoCin;
        this.montant = montant;
        this.dateEmission = dateEmission;
        this.dateApprobation = dateApprobation;
        this.etat = etat;
        this.type = type;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   /* public Compte getIdCompteEmetteur() {
        return idCompteEmetteur;
    }

    public void setIdCompteEmetteur(Compte idCompteEmetteur) {
        this.idCompteEmetteur = idCompteEmetteur;
    }

    public Compte getIdCompteBeneficiaire() {
        return idCompteBeneficiaire;
    }

    public void setIdCompteBeneficiaire(Compte idCompteBeneficiaire) {
        this.idCompteBeneficiaire = idCompteBeneficiaire;
    }*/

    public String getCinEmetteur() {
        return cinEmetteur;
    }

    public void setCinEmetteur(String cinEmetteur) {
        this.cinEmetteur = cinEmetteur;
    }

    public String getPhotoCin() {
        return photoCin;
    }

    public void setPhotoCin(String photoCin) {
        this.photoCin = photoCin;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public java.util.Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(java.util.Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public java.util.Date getDateApprobation() {
        return dateApprobation;
    }

    public void setDateApprobation(java.util.Date dateApprobation) {
        this.dateApprobation = dateApprobation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public TypeVirement getType() {
        return type;
    }

    public void setType(TypeVirement type) {
        this.type = type;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Virement virement = (Virement) o;
        return Objects.equals(id, virement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    @Override
    public int compareTo(Virement virement) {
        // Implement your comparison logic here
        return 0;
    }
}
