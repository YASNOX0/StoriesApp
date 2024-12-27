package com.example.storiesapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.storiesapp.model.Categorie;

import java.util.List;

@Dao
public interface CategorieDao {
    @Query("SELECT * FROM categorie")
    LiveData<List<Categorie>> getAll();

    @Query("SELECT * FROM categorie WHERE nom IN (:nomsCategories)")
    LiveData<List<Categorie>> loadAllByNames(int... nomsCategories);

    @Insert
    void insertAll(Categorie... categories);

    @Delete
    void deleteAll(Categorie... categories);

    @Update
    void updateAll(Categorie... categories);
}
