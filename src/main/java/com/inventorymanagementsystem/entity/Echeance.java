package com.inventorymanagementsystem.entity;

import java.sql.Date;

public class Echeance {
    private int id, idCredit;
    private String ModePyament,Etat;
    private Date Date;

    public Echeance() {
    }

    public Echeance(int id, int idCredit, String modePyament, String etat, java.sql.Date date) {
        this.id = id;
        this.idCredit = idCredit;
        ModePyament = modePyament;
        Etat = etat;
        Date = date;
    }
    public Echeance(int idCredit, String modePyament, String etat, java.sql.Date date) {
        this.idCredit = idCredit;
        ModePyament = modePyament;
        Etat = etat;
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(int idCredit) {
        this.idCredit = idCredit;
    }

    public String getModePyament() {
        return ModePyament;
    }

    public void setModePyament(String modePyament) {
        ModePyament = modePyament;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Echeance{" +
                "id=" + id +
                ", idCredit=" + idCredit +
                ", ModePyament='" + ModePyament + '\'' +
                ", Etat='" + Etat + '\'' +
                ", Date=" + Date +
                '}';
    }
}
