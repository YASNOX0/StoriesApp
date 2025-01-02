package com.example.storiesapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Auteur implements Serializable {
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
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
