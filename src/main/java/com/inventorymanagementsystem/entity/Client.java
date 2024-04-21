package com.inventorymanagementsystem.entity;

import java.util.Date;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String poste;
    private String email;
    private String password;
    private String photo;
    private Date dob;
    private long cin;
    private String adresse;
    private String etat;
    private String username;
    private long num;
    private String pays;
    private String document;

    public Client(int id, String nom, String prenom, String poste, String email, String password, String photo, Date dob, long cin, String adresse, String etat, String username, long num, String pays, String document) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.dob = dob;
        this.cin = cin;
        this.adresse = adresse;
        this.etat = etat;
        this.username = username;
        this.num = num;
        this.pays = pays;
        this.document = document;

    }

    public Client() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
   
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPoste() {
        return poste;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public Date getDob() {
        return dob;
    }

    public long getCin() {
        return cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEtat() {
        return etat;
    }

    public String getUsername() {
        return username;
    }

    public long getNum() {
        return num;
    }

    public String getPays() {
        return pays;
    }

    public String getDocument() {
        return document;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setCin(long cin) {
        this.cin = cin;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
