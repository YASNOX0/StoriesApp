package com.example.storiesapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pays {
    @PrimaryKey
    @NonNull
    private String nom;
    private String drapeau;

    public Pays() {

    }

    public Pays(String nom, String drapeau) {
        this.setNom(nom);
        this.setDrapeau(drapeau);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDrapeau() {
        return drapeau;
    }

    public void setDrapeau(String drapeau) {
        this.drapeau = drapeau;
    }
}
