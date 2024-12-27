package com.example.storiesapp.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Auteur.class,parentColumns = {"id"},
                childColumns = {"idAuteur"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Pays.class,parentColumns = {"nom"},
                childColumns = {"nomPays"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Categorie.class,parentColumns = {"nom"},
                childColumns = {"nomCategorie"},
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        )
})
public class Histoire {
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
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(long idAuteur) {
        this.idAuteur = idAuteur;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}
