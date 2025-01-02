package com.example.storiesapp.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.storiesapp.data.database.AppDatabase;
import com.example.storiesapp.data.dao.*;
import com.example.storiesapp.model.*;

import java.util.List;

public class Repository {
    private final HistoireDao histoireDao;
    private final AuteurDao auteurDao;
    private final CategorieDao categorieDao;
    private final PaysDao paysDao;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.histoireDao = db.histoireDao();
        this.auteurDao = db.auteurDao();
        this.categorieDao = db.categorieDao();
        this.paysDao = db.paysDao();
    }

    //region CRUD Histoire
    public LiveData<List<Histoire>> getAllHistoires() {
        return histoireDao.getAll();
    }

    public LiveData<List<Histoire>> getHistoiresByIds(int... idsHistoires) {
        return histoireDao.loadAllByIds(idsHistoires);
    }

    public LiveData<List<Histoire>> getHistoiresByPays(String nomPays) {
        return histoireDao.findByPays(nomPays);
    }

    public LiveData<List<Histoire>> getHistoiresByCategorie(String nomCategorie) {
        return histoireDao.findByCategories(nomCategorie);
    }

    public LiveData<List<Histoire>> getHistoiresByNomAuteur(String nomAuteur) {
        return histoireDao.findByNomAuteur(nomAuteur);
    }

    public void insertHistoires(Histoire... histoires) {
        AppDatabase.databaseWriteExecutor.execute(() -> histoireDao.insertAll(histoires));
    }

    public void updateHistoires(Histoire... histoires) {
        AppDatabase.databaseWriteExecutor.execute(() -> histoireDao.updateAll(histoires));
    }

    public void deleteHistoires(Histoire... histoires) {
        AppDatabase.databaseWriteExecutor.execute(() -> histoireDao.deleteAll(histoires));
    }
    //endregion

    //region CRUD Auteur
    public LiveData<List<Auteur>> getAllAuteurs() {
        return auteurDao.getAll();
    }

    public LiveData<List<Auteur>> getAuteursByIds(long... idsAuteurs) {
        return auteurDao.loadAllByIds(idsAuteurs);
    }

    public void insertAuteurs(Auteur... auteurs) {
        AppDatabase.databaseWriteExecutor.execute(() -> auteurDao.insertAll(auteurs));
    }

    public void updateAuteurs(Auteur... auteurs) {
        AppDatabase.databaseWriteExecutor.execute(() -> auteurDao.updateAll(auteurs));
    }

    public void deleteAuteurs(Auteur... auteurs) {
        AppDatabase.databaseWriteExecutor.execute(() -> auteurDao.deleteAll(auteurs));
    }
    //endregion

    //region CRUD Categorie
    public LiveData<List<Categorie>> getAllCategories() {
        return categorieDao.getAll();
    }

    public LiveData<List<Categorie>> getCategoriesByNames(String... categoriesNames) {
        return categorieDao.loadAllByNames(categoriesNames);
    }

    public void insertCategories(Categorie... categories) {
        AppDatabase.databaseWriteExecutor.execute(() -> categorieDao.insertAll(categories));
    }

    public void updateCategories(Categorie... categories) {
        AppDatabase.databaseWriteExecutor.execute(() -> categorieDao.updateAll(categories));
    }

    public void deleteCategories(Categorie... categories) {
        AppDatabase.databaseWriteExecutor.execute(() -> categorieDao.deleteAll(categories));
    }
    //endregion

    //region CRUD Pays
    public LiveData<List<Pays>> getAllPays() {
        return paysDao.getAll();
    }

    public LiveData<List<Pays>> getPaysByNames(String... nomsPays) {
        return paysDao.loadAllByNames(nomsPays);
    }

    public void insertPays(Pays... pays) {
        AppDatabase.databaseWriteExecutor.execute(() -> paysDao.insertAll(pays));
    }

    public void updatePays(Pays... pays) {
        AppDatabase.databaseWriteExecutor.execute(() -> paysDao.updateAll(pays));
    }

    public void deletePays(Pays... pays) {
        AppDatabase.databaseWriteExecutor.execute(() -> paysDao.deleteAll(pays));
    }
    //endregion

}
