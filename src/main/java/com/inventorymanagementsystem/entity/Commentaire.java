package com.inventorymanagementsystem.entity;

import java.util.Objects;

public class Commentaire {

    private Integer id;
    private Integer user;
    private Integer articleId;
    private String contenu;
    private Integer note;
    private java.util.Date date;

    // Constructor
    public Commentaire(){}
    public Commentaire(Integer id, Integer user, Integer articleId, String contenu, Integer note, java.util.Date date) {
        this.id = id;
        this.user = user;
        this.articleId = articleId;
        this.contenu = contenu;
        this.note = note;
        this.date = date;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user;
    }

    public void setUserId(Integer user) {
        this.user = user;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // compareTo method
    public int compareTo(Commentaire commentaire) {
        // Implement your comparison logic here
        return 0;
    }
}

