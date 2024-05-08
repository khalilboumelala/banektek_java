package com.inventorymanagementsystem.entity;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

    private Integer id;
    private String type;
    private java.util.Date dateTransaction;
    private Double montant;
    private int compte;

    // New property for selection
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    // Constructors
    public Transaction() {
    }

    public Transaction(Integer id, String type, java.util.Date dateTransaction, Double montant) {
        this.id = id;
        this.type = type;
        this.dateTransaction = dateTransaction;
        this.montant = montant;
    }

    public Transaction(Integer id, String type, java.util.Date dateTransaction, Double montant,int compte) {
        this.id = id;
        this.type = type;
        this.dateTransaction = dateTransaction;
        this.montant = montant;
        this.compte=compte;
    }


    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public java.util.Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(java.util.Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
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
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    @Override
    public int compareTo(Transaction transaction) {
        // Implement your comparison logic here
        return 0;
    }
}
