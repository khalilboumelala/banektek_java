package com.inventorymanagementsystem.entity;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

public class Client {
    private int id;
    private LocalDate dob;
    private String nom;
    private String prenom;
    private long cin;
    private long numTel;
    private String genre;
    private String pays;
    private String adresse;
    private String email;
    private String document;
    private String signature;
    private String profession;
    private LocalDate dateCreation;
    private String username;
    private String password;
    private LocalDate lastLogin;
    private String etat;
    private String photo;
    public Client(){};
    public Client(int id, LocalDate dob, String nom, String prenom, long cin, long numTel, String genre, String pays, String adresse, String email, String document, String signature, String profession, LocalDate dateCreation, String username, String password, LocalDate lastLogin, String etat, String photo) {
        this.id = id;
        this.dob = dob;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.numTel = numTel;
        this.genre = genre;
        this.pays = pays;
        this.adresse = adresse;
        this.email = email;
        this.document = document;
        this.signature = signature;
        this.profession = profession;
        this.dateCreation = dateCreation;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
        this.etat = etat;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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

    public long getCin() {
        return cin;
    }

    public void setCin(long cin) {
        this.cin = cin;
    }

    public long getNumTel() {
        return numTel;
    }

    public void setNumTel(long numTel) {
        this.numTel = numTel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int calculerAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }


    public static String generateStrongPassword(int length) {
         String CHARACTERS = "1234567890";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }
    public static String generateUsername(String prenom, String nom) {
        String prenomSubstring = prenom.substring(0, Math.min(prenom.length(), 3)).toLowerCase();
        String nomSubstring = nom.substring(0, Math.min(nom.length(), 3)).toLowerCase();

        int randomNum = (int) (Math.random() * 100);

        String randomString = String.format("%02d", randomNum);

        return prenomSubstring + nomSubstring + randomString;
    }

}
