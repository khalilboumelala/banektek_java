package com.inventorymanagementsystem.entity;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Article implements Comparable<Article> {

    private Integer id;
    private int id_agent;
    private String contenu;
    private String image;
    private String titre;
    private java.util.Date date_pub;

    // New property for selection
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    // Constructor
    public Article(){}
    public Article(Integer id, int id_agent, String contenu, String image, String titre, java.util.Date date_pub) {
        this.id = id;
        this.id_agent = id_agent;
        this.contenu = contenu;
        this.image = image;
        this.titre = titre;
        this.date_pub = date_pub;
      //  this.selected = new SimpleBooleanProperty(false); // Initialize with false
    }

    public Article(Integer id,String contenu,String titre,String image){
        this.id=id;
        this.contenu=contenu;
        this.titre=titre;
        this.image=image;
        //this.selected = new SimpleBooleanProperty(false); // Initialize with false
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdAgent() {
        return id_agent;
    }

    public void setIdAgent(int id_agent) {
        this.id_agent = id_agent;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public java.util.Date getDatePub() {
        return date_pub;
    }

    public void setDatePub(java.util.Date date_pub) {
        this.date_pub = date_pub;
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
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    @Override
    public int compareTo(Article article) {
        // Implement your comparison logic here
        return 0;
    }
}
