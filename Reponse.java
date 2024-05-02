package com.inventorymanagementsystem.entity;
import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;
public class Reponse implements Comparable<Reponse>{
    private Integer id ;
    private Integer id_reclamation_id;
    private Integer id_agent_id;
    private java.util.Date date_reponse;
    private String message;

    private Reclamation reclamation;

    public Reponse(Integer id,Integer id_reclamation_id,Integer id_agent_id,java.util.Date date_reponse,String message)
    {
        this.id=id;
        this.id_reclamation_id=id_reclamation_id;
        this.id_agent_id=id_agent_id;
        this.date_reponse=date_reponse;
        this.message=message;
    }

    //Getters Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_reclamation_id() {
        return id_reclamation_id;
    }

    public void setId_reclamation_id(Integer id_reclamation_id) {
        this.id_reclamation_id = id_reclamation_id;
    }

    public Integer getId_agent_id() {
        return id_agent_id;
    }

    public void setId_agent_id(Integer id_agent_id) {
        this.id_agent_id = id_agent_id;
    }

    public Date getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(Date date_reponse) {
        this.date_reponse = date_reponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse = (Reponse) o;
        return Objects.equals(id, reponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(Reponse reponse) {
        // Implement your comparison logic here
        return 0;
    }

    public Reponse() {
        // Initialisez éventuellement des attributs ici si nécessaire
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }
}


