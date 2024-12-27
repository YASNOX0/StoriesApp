package com.example.storiesapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Auteur {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nom;
    private String dateNaissance;

    public Auteur() {

    }

    public Auteur(long id,String nom, String dateNaissance) {
        this.setId(id);
        this.setNom(nom);
        this.setDateNaissance(dateNaissance);
    }

    public Auteur(String nom, String dateNaissance) {
        this.setId(id);
        this.setNom(nom);
        this.setDateNaissance(dateNaissance);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
