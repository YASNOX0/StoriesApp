package com.example.storiesapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Categorie implements Serializable {
    @PrimaryKey
    @NonNull
    private String nom;
    private String image;

    public Categorie() {
    }

    public Categorie(String nom, String image) {
        this.setNom(nom);
        this.setImage(image);
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
