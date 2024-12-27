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

    public Repository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.histoireDao = db.histoireDao();
        this.auteurDao = db.auteurDao();
        this.categorieDao = db.categorieDao();
        this.paysDao = db.paysDao();
    }

    //region CRUD Histoire
    public LiveData<List<Histoire>> getAllHistoires(){
        return histoireDao.getAll();
    }

    public LiveData<List<Histoire>> getHistoiresByIds(int... idsHistoires){
        return histoireDao.loadAllByIds(idsHistoires);
    }

    public LiveData<List<Histoire>> getHistoiresByPays(String nomPays){
        return histoireDao.findByPays(nomPays);
    }

    public LiveData<List<Histoire>> getHistoiresByCategorie(String nomCategorie){
        return histoireDao.findByCategories(nomCategorie);
    }


    public void insertHistoire(Histoire histoire){
        AppDatabase.databaseWriteExecutor.execute(() -> histoireDao.insertAll(histoire));
    }

    public void updateHistoire(Histoire histoire){
        AppDatabase.databaseWriteExecutor.execute(() -> histoireDao.updateAll(histoire));
    }

    public void deleteHistoire(Histoire histoire){
        AppDatabase.databaseWriteExecutor.execute(() -> histoireDao.deleteAll(histoire));
    }
    //endregion

    //region CRUD Auteur
    public LiveData<List<Auteur>> getAllAuteurs(){
        return auteurDao.getAll();
    }

    public LiveData<List<Auteur>> getAuteursByIds(int... idsAuteurs){
        return auteurDao.loadAllByIds(idsAuteurs);
    }

    public void insertAuteur(Auteur auteur){
        AppDatabase.databaseWriteExecutor.execute(() -> auteurDao.insertAll(auteur));
    }

    public void updateAuteur(Auteur auteur){
        AppDatabase.databaseWriteExecutor.execute(() ->auteurDao.updateAll(auteur));
    }

    public void deleteAuteur(Auteur auteur){
        AppDatabase.databaseWriteExecutor.execute(() -> auteurDao.deleteAll(auteur));
    }
    //endregion

    //region CRUD Categorie
    public LiveData<List<Categorie>> getAllCategories(){
        return categorieDao.getAll();
    }

    public LiveData<List<Categorie>> getCategoriesByNames(int... idsCategories){
        return categorieDao.loadAllByNames(idsCategories);
    }

    public void insertCategorie(Categorie categorie){
        AppDatabase.databaseWriteExecutor.execute(()->categorieDao.insertAll(categorie));
    }

    public void updateCategorie(Categorie categorie){
        AppDatabase.databaseWriteExecutor.execute(()->categorieDao.updateAll(categorie));
    }

    public void deleteCategorie(Categorie categorie){
        AppDatabase.databaseWriteExecutor.execute(()->categorieDao.deleteAll(categorie));
    }
    //endregion

    //region CRUD Pays
    public LiveData<List<Pays>> getAllPays(){
        return paysDao.getAll();
    }

    public LiveData<List<Pays>> getPaysByNames(int... idsPays){
        return paysDao.loadAllByNames(idsPays);
    }

    public void insertPays(Pays pays){
        AppDatabase.databaseWriteExecutor.execute(()->paysDao.insertAll(pays));
    }

    public void updatePays(Pays pays){
        AppDatabase.databaseWriteExecutor.execute(()->paysDao.updateAll(pays));
    }

    public void deletePays(Pays pays){
        AppDatabase.databaseWriteExecutor.execute(()->paysDao.deleteAll(pays));
    }
    //endregion

}
