package com.example.storiesapp.model;

import androidx.lifecycle.Observer;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.storiesapp.viewmodel.AppViewModel;

import java.io.Serializable;
import java.util.List;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Auteur.class, parentColumns = {"id"},
                childColumns = {"idAuteur"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Pays.class, parentColumns = {"nom"},
                childColumns = {"nomPays"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Categorie.class, parentColumns = {"nom"},
                childColumns = {"nomCategorie"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        )
})
public class Histoire implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String titre;
    private String image;
    private String contenu;
    private String date;
    private long idAuteur;
    private String nomPays;
    private String nomCategorie;

    public Histoire() {

    }

    public Histoire(long id, String titre, String image, String contenu, String date, long idAuteur, String nomPays, String nomCategorie) {
        this.setId(id);
        this.setTitre(titre);
        this.setImage(image);
        this.setContenu(contenu);
        this.setDate(date);
        this.setIdAuteur(idAuteur);
        this.setNomPays(nomPays);
        this.setNomCategorie(nomCategorie);
    }

    public Histoire(String titre, String image, String contenu, String date, long idAuteur, String nomPays, String nomCategorie) {
        this.setTitre(titre);
        this.setImage(image);
        this.setContenu(contenu);
        this.setDate(date);
        this.setIdAuteur(idAuteur);
        this.setNomPays(nomPays);
        this.setNomCategorie(nomCategorie);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContenu() {
        return this.contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getIdAuteur() {
        return this.idAuteur;
    }

    public void setIdAuteur(long idAuteur) {
        this.idAuteur = idAuteur;
    }

    public String getNomPays() {
        return this.nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public String getNomCategorie() {
        return this.nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

}
