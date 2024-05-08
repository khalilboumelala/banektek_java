package com.inventorymanagementsystem.entity;


import java.sql.Date;
public class Credit implements Comparable<Credit> {

    private Integer id;
    private Integer id_compte_id;
    private Double montant;
    private Double taux;
    private Integer duree;
    private Date date_debut;
    private String type;
    private Double apport_propre;
    private Double revenu_propre;
    private Double montant_echeance;
    private Integer date_echeance;
    private String etat;
    private Integer nb_echeances_restants;

    public Credit(Integer id_compte_id, Double montant, Double taux, Integer duree, Date date_debut, String type, Double apport_propre, Double revenu_propre, Double montant_echeance, Integer date_echeance, String etat, Integer nb_echeances_restants) {
        this.id_compte_id = id_compte_id;
        this.montant = montant;
        this.taux = taux;
        this.duree = duree;
        this.date_debut = date_debut;
        this.type = type;
        this.apport_propre = apport_propre;
        this.revenu_propre = revenu_propre;
        this.montant_echeance = montant_echeance;
        this.date_echeance = date_echeance;
        this.etat = etat;
        this.nb_echeances_restants = nb_echeances_restants;
    }

    public Credit(Integer id, Integer id_compte_id, Double montant, Double taux, Integer duree, Date date_debut, String type, Double apport_propre, Double revenu_propre, Double montant_echeance, Integer date_echeance, String etat, Integer nb_echeances_restants) {
        this.id = id;
        this.id_compte_id = id_compte_id;
        this.montant = montant;
        this.taux = taux;
        this.duree = duree;
        this.date_debut = date_debut;
        this.type = type;
        this.apport_propre = apport_propre;
        this.revenu_propre = revenu_propre;
        this.montant_echeance = montant_echeance;
        this.date_echeance = date_echeance;
        this.etat = etat;
        this.nb_echeances_restants = nb_echeances_restants;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", id_compte_id=" + id_compte_id +
                ", montant=" + montant +
                ", taux=" + taux +
                ", duree=" + duree +
                ", date_debut=" + date_debut +
                ", type='" + type + '\'' +
                ", apport_propre=" + apport_propre +
                ", revenu_propre=" + revenu_propre +
                ", montant_echeance=" + montant_echeance +
                ", date_echeance=" + date_echeance +
                ", etat='" + etat + '\'' +
                ", nb_echeances_restants=" + nb_echeances_restants +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId_compte_id(Integer id_compte_id) {
        this.id_compte_id = id_compte_id;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setApport_propre(Double apport_propre) {
        this.apport_propre = apport_propre;
    }

    public void setrevenu_propre(Double revenu_propre) {
        this.revenu_propre = revenu_propre;
    }

    public void setmontant_echeance(Double montant_echeance) {
        this.montant_echeance = montant_echeance;
    }

    public void setDate_echeance(Integer date_echeance) {
        this.date_echeance = date_echeance;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setNb_echeances_restants(Integer nb_echeances_restants) {
        this.nb_echeances_restants = nb_echeances_restants;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_compte_id() {
        return id_compte_id;
    }

    public Double getMontant() {
        return montant;
    }

    public Double getTaux() {
        return taux;
    }

    public Integer getDuree() {
        return duree;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public String getType() {
        return type;
    }

    public Double getApport_propre() {
        return apport_propre;
    }

    public Double getrevenu_propre() {
        return revenu_propre;
    }

    public Double getMontant_echeance() {
        return montant_echeance;
    }

    public Integer getDate_echeance() {
        return date_echeance;
    }

    public String getEtat() {
        return etat;
    }

    public Integer getNb_echeances_restants() {
        return nb_echeances_restants;
    }

    @Override
    public int compareTo( Credit credit) {
        return 0;
    }



}