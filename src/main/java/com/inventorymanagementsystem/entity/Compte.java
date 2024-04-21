    package com.inventorymanagementsystem.entity;

    import javafx.beans.property.SimpleBooleanProperty;

    import java.util.Objects;

    public class Compte implements Comparable<Compte> {

        private Integer id;
        private Integer idUser;
        private Integer idAgence;
        private String type;
        private Double solde;
        private java.util.Date dateCreation;
        private java.util.Date dateFermeture;
        private String etat;
        private String rib;

        // New property for selection
        private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

        // Constructor
        public Compte() {
        }

        public Compte(Integer id, Integer idUser, Integer idAgence, String type, Double solde, java.util.Date dateCreation, java.util.Date dateFermeture, String etat, String rib) {
            this.id = id;
            this.idUser = idUser;
            this.idAgence = idAgence;
            this.type = type;
            this.solde = solde;
            this.dateCreation = dateCreation;
            this.dateFermeture = dateFermeture;
            this.etat = etat;
            this.rib = rib;
        }

        // Getters and Setters
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }

        public Integer getIdAgence() {
            return idAgence;
        }

        public void setIdAgence(Integer idAgence) {
            this.idAgence = idAgence;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Double getSolde() {
            return solde;
        }

        public void setSolde(Double solde) {
            this.solde = solde;
        }

        public java.util.Date getDateCreation() {
            return dateCreation;
        }

        public void setDateCreation(java.util.Date dateCreation) {
            this.dateCreation = dateCreation;
        }

        public java.util.Date getDateFermeture() {
            return dateFermeture;
        }

        public void setDateFermeture(java.util.Date dateFermeture) {
            this.dateFermeture = dateFermeture;
        }

        public String getEtat() {
            return etat;
        }

        public void setEtat(String etat) {
            this.etat = etat;
        }

        public String getRib() {
            return rib;
        }

        public void setRib(String rib) {
            this.rib = rib;
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
            Compte compte = (Compte) o;
            return Objects.equals(id, compte.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        // compareTo method
        @Override
        public int compareTo(Compte compte) {
            // Implement your comparison logic here
            return 0;
        }
    }
