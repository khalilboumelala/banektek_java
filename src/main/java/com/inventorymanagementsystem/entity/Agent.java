package com.inventorymanagementsystem.entity;

public class Agent {
    private int id;
    private int idAgence;
    private String nom;
    private String prenom;
    private String poste;
    private String matricule;
    private String password;
    private String email;
    private String numTel;
    private String photo;
    private String faceId;

    public Agent(int id, int idAgence, String nom, String prenom, String poste, String matricule, String password, String email, String numTel, String photo, String faceId) {
        this.id = id;
        this.idAgence = idAgence;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.matricule = matricule;
        this.password = password;
        this.email = email;
        this.numTel = numTel;
        this.photo = photo;
        this.faceId = faceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(int idAgence) {
        this.idAgence = idAgence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}
