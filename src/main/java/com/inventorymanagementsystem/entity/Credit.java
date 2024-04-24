package com.inventorymanagementsystem.entity;
import java.sql.Date;

public class Credit {
    private int idcredit,id,dure,date_echeance,nb_echeances_restants;
    private float montant,taux,apport_propre,revenu_propre,montant_echeance;
    private String type,etat;
    private Date date_debut;

    public Credit() {}
    //Constructeur bel ID
    public Credit(int id, int dure, int date_echeance, int nb_echeances_restants, float montant, float taux, float apport_propre, float revenu_propre, float montant_echeance, String type, String etat, Date date_debut) {
        this.id = id;
        this.dure = dure;
        this.date_echeance = date_echeance;
        this.nb_echeances_restants = nb_echeances_restants;
        this.montant = montant;
        this.taux = taux;
        this.apport_propre = apport_propre;
        this.revenu_propre = revenu_propre;
        this.montant_echeance = montant_echeance;
        this.type = type;
        this.etat = etat;
        this.date_debut = date_debut;
    }
    //Constructeur maghir ID
    public Credit(int dure, int date_echeance, int nb_echeances_restants, float montant, float taux, float apport_propre, float revenu_propre, float montant_echeance, String type, String etat, Date date_debut) {
        this.dure = dure;
        this.date_echeance = date_echeance;
        this.nb_echeances_restants = nb_echeances_restants;
        this.montant = montant;
        this.taux = taux;
        this.apport_propre = apport_propre;
        this.revenu_propre = revenu_propre;
        this.montant_echeance = montant_echeance;
        this.type = type;
        this.etat = etat;
        this.date_debut = date_debut;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getIdcredit() {return idcredit;}
    public void setIdcredit(int idcredit) {this.idcredit = idcredit;}

    public int getDure() {return dure;}
    public void setDure(int dure) {this.dure = dure;}

    public int getDate_echeance() {return date_echeance;}
    public void setDate_echeance(int date_echeance) {this.date_echeance = date_echeance;}

    public int getNb_echeances_restants() {return nb_echeances_restants;}
    public void setNb_echeances_restants(int nb_echeances_restants) {this.nb_echeances_restants = nb_echeances_restants;}

    public float getMontant() {return montant;}
    public void setMontant(float montant) {this.montant = montant;}

    public float getTaux() {return taux;}
    public void setTaux(float taux) {this.taux = taux;}

    public float getApport_propre() {return apport_propre;}
    public void setApport_propre(float apport_propre) {this.apport_propre = apport_propre;}

    public float getRevenu_propre() {return revenu_propre;}
    public void setRevenu_propre(float revenu_propre) {this.revenu_propre = revenu_propre;}

    public float getMontant_echeance() {return montant_echeance;}
    public void setMontant_echeance(float montant_echeance) {this.montant_echeance = montant_echeance;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getEtat() {return etat;}
    public void setEtat(String etat) {this.etat = etat;}

    public Date getDate_debut() {return date_debut;}
    public void setDate_debut(Date date_debut) {this.date_debut = date_debut;}

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", dure=" + dure +
                ", date_echeance=" + date_echeance +
                ", nb_echeances_restants=" + nb_echeances_restants +
                ", montant=" + montant +
                ", taux=" + taux +
                ", apport_propre=" + apport_propre +
                ", revenu_propre=" + revenu_propre +
                ", montant_echeance=" + montant_echeance +
                ", type='" + type + '\'' +
                ", etat='" + etat + '\'' +
                ", date_debut=" + date_debut +
                '}';
    }
}
