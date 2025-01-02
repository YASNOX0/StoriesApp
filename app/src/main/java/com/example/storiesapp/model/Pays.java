package com.example.storiesapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Pays implements Serializable {
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
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDrapeau() {
        return this.drapeau;
    }

    public void setDrapeau(String drapeau) {
        this.drapeau = drapeau;
    }
}
